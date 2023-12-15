package blog.example.BlogApplication2.ControllerTest;

import blog.example.BlogApplication2.Controller.LikeController;
import blog.example.BlogApplication2.Service.LikeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class LikeControllerTest {
    @InjectMocks
    private LikeController likeController;
    @Mock
    private LikeService likeService;
    @BeforeEach
     public void setup(){
         MockitoAnnotations.openMocks(this);
     }
     @Test
     public void testLikepost(){
        when(likeService.likePost(anyInt(),anyInt())).thenReturn("Liked");
         ResponseEntity<Object> response=likeController.likePost(1,1);
         assertEquals("Liked",response.getBody());
     }
     @Test
     public void testUnlikePost(){
        when(likeService.unlikePost(anyInt(),anyInt())).thenReturn("Unliked");
        ResponseEntity<String> response=likeController.unlikePost(1,1);
        assertEquals("Unliked",response.getBody());
     }
     @Test
     public  void testLikeComment(){
        when(likeService.likeComment(anyInt(),anyInt())).thenReturn("Comment Liked");
        ResponseEntity<String> response=likeController.likeComment(1,1);
        assertEquals("Comment Liked",response.getBody());
     }
     @Test
     public void  testUnlikeComment(){
        when(likeService.unlikeComment(anyInt())).thenReturn("Comment Unliked");
        ResponseEntity<String> response=likeController.unlikeComment(1,1);
        assertEquals("Comment Unliked",response.getBody());
     }
}
