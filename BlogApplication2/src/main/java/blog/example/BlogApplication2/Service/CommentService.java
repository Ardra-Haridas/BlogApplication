package blog.example.BlogApplication2.Service;

import blog.example.BlogApplication2.Repository.*;
import blog.example.BlogApplication2.Model.*;
import blog.example.BlogApplication2.Util.AppContext;
import jakarta.persistence.PreRemove;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CommentService {
private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final HistoryRepository historyRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository,PostRepository postRepository,UserRepository userRepository,LikeRepository likeRepository,HistoryRepository historyRepository){
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.likeRepository=likeRepository;
        this.historyRepository=historyRepository;
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
            if(createcommentRequest.getParentcommentid() !=null){
                Comment parentComment=commentRepository.findById(createcommentRequest.getParentcommentid()).orElse(null);
                comment.setParentComment(parentComment);
            }
            comment.setCreationdate(createcommentRequest.getCreationdate());
            comment.setLastmodifieddate(createcommentRequest.getLastmodifieddate());
            commentRepository.save(comment);
            return "comment created!";
        }else{
            return "can't create";
        }


    }

    @PreRemove
    public void deleteComment(Integer commentid) throws Exception {
        try{
            Comment deletedComment=commentRepository.findById(commentid).orElse(null);
            if(deletedComment!=null){
                History history=new History();
                history.setOldcomment(deletedComment.getContent());
                history.setTime(new Date());
                history.setChangetype(ChangeType.DELETE);
                history.setComment(deletedComment);
                historyRepository.save(history);


            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Transactional
    public Comment updateComment(Integer commentid,Comment updatedComment){
        Comment existingComment=commentRepository.findById(commentid).orElse(null);
        if(existingComment !=null){
            History history=new History();
            history.setOldcomment(existingComment.getContent());
            history.setChangetype(ChangeType.EDIT);
            history.setTime(new Date());
            historyRepository.save(history);
            existingComment.setContent(updatedComment.getContent());
            return commentRepository.save(existingComment);
        }
        return null;
    }

}
