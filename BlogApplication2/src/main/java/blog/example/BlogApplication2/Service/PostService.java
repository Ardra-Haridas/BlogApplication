package blog.example.BlogApplication2.Service;
import blog.example.BlogApplication2.Model.BlogPostHistory;
import blog.example.BlogApplication2.Repository.BlogPostHistoryRepository;
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
    public String getImageRelativePath(Integer postId) {
        return "/Users/ardra.h/Downloads/Blog";
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
