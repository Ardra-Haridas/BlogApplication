package blog.example.BlogApplication2.ServiceTest;

import blog.example.BlogApplication2.Model.Blogpost;
import blog.example.BlogApplication2.Model.CreatecommentRequest;
import blog.example.BlogApplication2.Model.User;
import blog.example.BlogApplication2.Repository.CommentRepository;
import blog.example.BlogApplication2.Repository.LikeRepository;
import blog.example.BlogApplication2.Repository.PostRepository;
import blog.example.BlogApplication2.Repository.UserRepository;
import blog.example.BlogApplication2.Service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CommentServiceTest {
    @InjectMocks
    private CommentService commentService;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private LikeRepository likeRepository;
    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public  void testSaveComment(){
        CreatecommentRequest request=CreatecommentRequest.builder()
                .userid(1)
                .postid(1)
                .parentcommentid(null)
                .content("Sample Comment")
                .creationdate(new Date())
                .lastmodifieddate(new Date())
                .build();

        when(postRepository.findById(1)).thenReturn(Optional.of(new Blogpost()));
        when(userRepository.findById(1)).thenReturn(Optional.of(new User()));
        String result= commentService.saveComment(request);
        assertEquals("comment created!",result);
    }
@Test
    public void testDeleteComment(){
        int commentId =1;
        doNothing().when(likeRepository).deleteByCommentId(commentId);
        doNothing().when(commentRepository).deleteById(commentId);
        commentService.deleteComment(commentId);
        verify(likeRepository,times(1)).deleteByCommentId(commentId);
        verify(commentRepository,times(1)).deleteById(commentId);
    }

}
