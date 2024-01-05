package blog.example.BlogApplication2.Service;
import blog.example.BlogApplication2.Model.ChangeType;
import blog.example.BlogApplication2.Model.History;
import blog.example.BlogApplication2.Repository.HistoryRepository;
import blog.example.BlogApplication2.Repository.LikeRepository;
import blog.example.BlogApplication2.Repository.PostRepository;
import blog.example.BlogApplication2.Model.Blogpost;
import jakarta.persistence.PreRemove;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;

@Service
public class PostService {

    private PostRepository postRepository;
    private LikeRepository likeRepository;

    private ImageUploadService imageUploadService;
    private HistoryRepository historyRepository;

    @Autowired
    public PostService(PostRepository postRepository,
                       LikeRepository likeRepository,
                       ImageUploadService imageUploadService,
                       HistoryRepository historyRepository) {
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
        this.imageUploadService=imageUploadService;
        this.historyRepository=historyRepository;

    }

    public List<Blogpost> getAllPosts() {

        return postRepository.findAll();
    }
    public Blogpost getpostById(Integer postid){

        return postRepository.findById(postid).orElse(null);
    }
    public Blogpost createBlogpost(Blogpost blogpost) {

        return postRepository.save(blogpost);
    }

    @PreRemove
    public void deleteBlogpost(Integer postid) throws Exception {
        try {
            Blogpost deletedPost=postRepository.findById(postid).orElse(null);
            if(deletedPost!=null) {
                History history = new History();
                history.setOldcontent(deletedPost.getContent());
                history.setOldtitle(deletedPost.getTitle());
                history.setOldimage(deletedPost.getImage());
                history.setTime(new Date());
                history.setChangetype(ChangeType.DELETE);
                history.setBlogpost(deletedPost);
                historyRepository.save(history);
                likeRepository.deleteByPostId(postid);
                postRepository.deleteById(postid);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void uploadImage(Integer postId, MultipartFile imageFile) {
        imageUploadService.uploadImage(postId, imageFile);
    }
    public byte[] getImageRelativePath(Integer postId) throws IOException {
        Blogpost blogpost=postRepository.findById(postId).orElse(null);
        String filepath= "/Users/ardra.h/Downloads/BlogApplication2/BlogApplication2/src/main/java/blog/example/BlogApplication2/Images/"+postId+"/"+blogpost.getImage();
        byte[] image= Files.readAllBytes(new File(filepath).toPath());
        return image;
    }


    @Transactional
    public Blogpost updateBlogpost(Integer postid, Blogpost updatedPost) {
        Blogpost existingPost = postRepository.findById(postid).orElse(null);

        if (existingPost != null) {
            History history = new History();
           history.setOldcontent(existingPost.getContent());
           history.setOldtitle(existingPost.getTitle());
           history.setBlogpost(existingPost);
           history.setOldimage(existingPost.getImage());
           history.setChangetype(ChangeType.EDIT);
           history.setTime(new Date());
           historyRepository.save(history);
           existingPost.setTitle(updatedPost.getTitle());
           existingPost.setContent(updatedPost.getContent());
           existingPost.setImage(updatedPost.getImage());
           return postRepository.save(existingPost);
        }
        return null;
    }


}
