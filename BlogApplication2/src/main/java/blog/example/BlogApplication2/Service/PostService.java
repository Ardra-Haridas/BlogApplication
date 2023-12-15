package blog.example.BlogApplication2.Service;
import blog.example.BlogApplication2.Model.BlogPostHistory;
import blog.example.BlogApplication2.Model.Comment;
import blog.example.BlogApplication2.Repository.BlogPostHistoryRepository;
import blog.example.BlogApplication2.Repository.LikeRepository;
import blog.example.BlogApplication2.Repository.PostRepository;
import blog.example.BlogApplication2.Model.Blogpost;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private PostRepository postRepository;
    private LikeRepository likeRepository;

    private ImageUploadService imageUploadService;
    private BlogPostHistoryRepository blogPostHistoryRepository;

    @Autowired
    public PostService(PostRepository postRepository,
                       LikeRepository likeRepository,
                       ImageUploadService imageUploadService,
                       BlogPostHistoryRepository blogPostHistoryRepository) {
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
        this.imageUploadService=imageUploadService;
        this.blogPostHistoryRepository=blogPostHistoryRepository;

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

    @Transactional
    public boolean deleteBlogpost(Integer postid) {
        try {

            likeRepository.deleteByPostId(postid);
            postRepository.deleteById(postid);
            return true;

        } catch (Exception e) {
            return false;
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
            BlogPostHistory history = new BlogPostHistory();
            history.setNewcontent(existingPost.getContent()); 
            history.setNewtitle(existingPost.getTitle());
            history.setNewimage(existingPost.getImage());
            history.setBlogpost(existingPost);
            history.setTime(new Date());
            blogPostHistoryRepository.save(history);
            existingPost.setContent(updatedPost.getContent());
            existingPost.setTitle(updatedPost.getTitle());
            existingPost.setImage(updatedPost.getImage());

            return postRepository.save(existingPost);
        }
        return null;
    }


}
