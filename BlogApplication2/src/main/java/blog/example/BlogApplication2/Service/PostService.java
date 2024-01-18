package blog.example.BlogApplication2.Service;
import blog.example.BlogApplication2.Model.*;
import blog.example.BlogApplication2.Repository.*;
import jakarta.persistence.PreRemove;
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
    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;

    private ImageUploadService imageUploadService;
    private HistoryRepository historyRepository;

    @Autowired
    public PostService(PostRepository postRepository,
                       LikeRepository likeRepository,
                       UserRepository userRepository, CommunityRepository communityRepository, ImageUploadService imageUploadService,
                       HistoryRepository historyRepository) {
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.communityRepository = communityRepository;
        this.imageUploadService=imageUploadService;
        this.historyRepository=historyRepository;

    }

    public List<Blogpost> getAllPosts() {

        return postRepository.findAll();
    }
    public Blogpost getpostById(Integer postid){

        return postRepository.findById(postid).orElse(null);
    }
    public Blogpost createBlogpost(BlogPostRequest blogPostRequest) {
        User currentUser = userRepository.findById(blogPostRequest.getUserid()).orElse(null);
        if (currentUser!=null){
            Blogpost blogpost = new Blogpost();
            blogpost.setTitle(blogPostRequest.getTitle());
            blogpost.setContent(blogPostRequest.getContent());
            blogpost.setUser(currentUser);
            if (blogPostRequest.getCommunityid()!=null) {
                Community community = communityRepository.findById(blogPostRequest.getCommunityid()).orElse(null);
                blogpost.setCommunity(community);
            }
            return postRepository.save(blogpost);
        }else {
            throw new RuntimeException();
        }

    }

    @PreRemove
    public void deleteBlogpost(Integer postid) throws Exception {
        try {
            Blogpost deletedPost=postRepository.findById(postid).orElse(null);
            if(deletedPost!=null) {
                History history = new History();
                history.setTime(new Date());
                history.setChangetype(ChangeType.DELETE);
                history.setBlogpost(deletedPost);
                historyRepository.save(history);

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
           return postRepository.save(existingPost);
        }
        return null;
    }


    public List<Blogpost> getAllPostsByCommunityId(Integer communityid) {
        return postRepository.findByCommunityId(communityid);
    }
}
