package blog.example.BlogApplication2.ServiceTest;

import blog.example.BlogApplication2.Model.Blogpost;
import blog.example.BlogApplication2.Model.Comment;
import blog.example.BlogApplication2.Model.Likes;
import blog.example.BlogApplication2.Model.User;
import blog.example.BlogApplication2.Repository.CommentRepository;
import blog.example.BlogApplication2.Repository.LikeRepository;
import blog.example.BlogApplication2.Repository.PostRepository;
import blog.example.BlogApplication2.Repository.UserRepository;
import blog.example.BlogApplication2.Service.LikeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LikeServiceTest {
    @InjectMocks
    private LikeService likeService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private LikeRepository likeRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private CommentRepository commentRepository;
    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }
//    @Test
//    void testlikePost(){
//        when(likeRepository.existsByPostIdAndUserId(1,1)).thenReturn(0);
//        when(postRepository.findById(1)).thenReturn(Optional.of(new Blogpost()));
//        when(userRepository.findById(1)).thenReturn(Optional.of(new User()));
//        when(likeRepository.save(any())).thenReturn(new Likes());
//        String result = likeService.likePost(1,1);
//        assertEquals("you liked the post",result);
//        verify(likeRepository,times(1)).save(any());
//    }
    @Test
    void  testUnlikePost(){
        when(likeRepository.existsByPostIdAndUserId(1,1)).thenReturn(1);
      String result=likeService.unlikePost(1,1);
      assertEquals("You have unlike the Post",result);
        verify(likeRepository, times(1)).deleteByPostIdAndUserId(1, 1);
    }
   // @Test
//void testLikeComment(){
//        when(likeRepository.existsByCommentIdAndUserId(1)).thenReturn(0);
//        when(commentRepository.findById(1)).thenReturn(Optional.of(new Comment()));
//        when(userRepository.findById(1)).thenReturn(Optional.of(new User()));
//        when(likeRepository.save(any())).thenReturn(new Likes());
//        String result=likeService.likeComment(1,1);
//        assertEquals("you liked the comment",result);
//        verify(likeRepository,times(1)).save(any());
//}
@Test
void  testUnlikeComment(){
        when(likeRepository.existsByCommentIdAndUserId(1)).thenReturn(1);
        String result=likeService.unlikeComment(1);
        verify(likeRepository,times(1)).deleteByCommentId(1);
}
}
