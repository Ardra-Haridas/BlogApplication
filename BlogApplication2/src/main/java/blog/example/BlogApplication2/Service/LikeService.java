package blog.example.BlogApplication2.Service;

import blog.example.BlogApplication2.Repository.CommentRepository;
import blog.example.BlogApplication2.Repository.LikeRepository;
import blog.example.BlogApplication2.Repository.PostRepository;
import blog.example.BlogApplication2.Repository.UserRepository;
import blog.example.BlogApplication2.Model.Blogpost;
import blog.example.BlogApplication2.Model.Comment;
import blog.example.BlogApplication2.Model.Likes;
import blog.example.BlogApplication2.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LikeService {

    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    @Autowired
    public LikeService(UserRepository userRepository, LikeRepository likeRepository, PostRepository postRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }
    public Object likePost(Integer postid,Integer userid){
    if(likeRepository.existsByPostIdAndUserId(postid, userid)!=0){
        return "You've already liked the post";
    }
        Blogpost blogpost=postRepository.findById(postid).orElse(null);
        User user =userRepository.findById(userid).orElse(null);

        if (blogpost !=null && user !=null){
           Likes likes=new Likes();
           likes.setUser(user);
           likes.setBlogpost(blogpost);
            likes.setLiketime(new Date());
            likeRepository.save(likes);
            return likes;
        }else{
            return "Unable to like post";
        }
    }
    public String unlikePost(Integer postid,Integer userid){
        if(likeRepository.existsByPostIdAndUserId(postid,userid)==0){
            return "You haven't liked the Post";
        }
        likeRepository.deleteByPostIdAndUserId(postid,userid);
        return "success";
    }
    public Integer getAllLikesByUserandPost(Integer userid, Integer postid){
        return likeRepository.findAllByUserIdAndPostId(userid,postid);
    }
    public Object likeComment(Integer commentid,Integer userid){
        if(likeRepository.existsByCommentIdAndUserId(commentid)!=0){
            return "You've already liked the comment";
        }
        Comment comment=commentRepository.findById(commentid).orElse(null);
        User user=userRepository.findById(userid).orElse(null);

        if(comment!=null && user!=null){
            Likes likes= new Likes();
            likes.setUser(user);
            likes.setComment(comment);
            likes.setLiketime(new Date());
            likeRepository.save(likes);
            return "you liked the comment";
        }else{
            return "Unable to like comment";
        }
        }

        public String unlikeComment(Integer commentid){
        if(likeRepository.existsByCommentIdAndUserId(commentid)==0){
            return "You haven't liked the Comment";
        }
        likeRepository.deleteByCommentId(commentid);
        return "You unliked the Comment";
        }


}
