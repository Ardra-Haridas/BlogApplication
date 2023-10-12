package blog.example.BlogApplication2.Service;
import blog.example.BlogApplication2.Model.BlogpostHistory;
import blog.example.BlogApplication2.Model.User;
import blog.example.BlogApplication2.Repository.BlogpostHistoryRepository;
import blog.example.BlogApplication2.Repository.LikeRepository;
import blog.example.BlogApplication2.Repository.PostRepository;
import blog.example.BlogApplication2.Model.Blogpost;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
public class PostService {

    private PostRepository postRepository;
    private LikeRepository likeRepository;
    private BlogpostHistoryRepository blogpostHistoryRepository;
    private ImageUploadService imageUploadService;

    @Autowired
    public PostService(PostRepository postRepository, LikeRepository likeRepository,BlogpostHistoryRepository blogpostHistoryRepository,ImageUploadService imageUploadService) {
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
        this.blogpostHistoryRepository=blogpostHistoryRepository;
        this.imageUploadService=imageUploadService;

    }

    public List<Blogpost> getAllPosts() {
        return postRepository.findAll();
    }

    public Blogpost createBlogpost(Blogpost blogpost) {
        return postRepository.save(blogpost);
    }
    public  Blogpost getPostById(Integer postid){
        return (Blogpost) postRepository.findById(postid).orElse(null);
    }
    @Transactional
   public Blogpost updateBlogpost(Integer postid,Blogpost updatedPost){
    return postRepository.save(updatedPost);
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
    public String getImageRelativePath(Integer postId) {
        return "C:\Users\\ardra.h\\Downloads\\Blog";
    }

}
