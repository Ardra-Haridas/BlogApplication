package blog.example.BlogApplication2.Service;

import blog.example.BlogApplication2.Repository.CommentRepository;
import blog.example.BlogApplication2.Repository.LikeRepository;
import blog.example.BlogApplication2.Repository.PostRepository;
import blog.example.BlogApplication2.Repository.UserRepository;
import blog.example.BlogApplication2.Model.*;
import blog.example.BlogApplication2.Util.AppContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class CommentService {
private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository,PostRepository postRepository,UserRepository userRepository,LikeRepository likeRepository){
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.likeRepository=likeRepository;
    }

public List<Comment>getAllComments(){
    return commentRepository.findAll();
}
public List<Map<String,Object>> getCommentsByPostId(Integer postid) {
        User currentUser = userRepository.findByEmail(AppContext.getEmail()).orElse(null);
            Blogpost blogpost = postRepository.findById(postid).orElse(null);
            if (blogpost != null && currentUser != null) {
                return commentRepository.findByBlogpost(blogpost.getId(), currentUser.getId());
            } else {
                return Collections.emptyList();
            }
        }


    public Comment getcommentById(Integer commentid){
        return commentRepository.findById(commentid).orElse(null);
}
    public String saveComment(CreatecommentRequest createcommentRequest) {
        Blogpost blogpost= postRepository.findById(createcommentRequest.getPostid()).orElse(null);
        User user=userRepository.findById(createcommentRequest.getUserid()).orElse(null);
        if(blogpost!=null && user!=null){
            Comment comment = new Comment();
            comment.setContent(createcommentRequest.getContent());
            comment.setUser(user);
            comment.setBlogpost(blogpost);
            comment.setParentComment(createcommentRequest.getParentcommentid());
            comment.setCreationdate(createcommentRequest.getCreationdate());
            comment.setLastmodifieddate(createcommentRequest.getLastmodifieddate());
            commentRepository.save(comment);
            return "comment created!";
        }else{
            return "can't create";
        }


    }
    public void deleteComment(Integer commentid) {
        likeRepository.deleteByCommentId(commentid);
        commentRepository.deleteById(commentid);
    }

}
