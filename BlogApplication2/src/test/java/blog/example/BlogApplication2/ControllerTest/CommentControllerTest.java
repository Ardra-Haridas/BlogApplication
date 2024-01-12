package blog.example.BlogApplication2.ControllerTest;

import blog.example.BlogApplication2.Controller.CommentController;
import blog.example.BlogApplication2.Model.Comment;
import blog.example.BlogApplication2.Model.CreatecommentRequest;
import blog.example.BlogApplication2.Service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CommentControllerTest {

    private CommentController commentController;

    @Mock
    private CommentService commentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        commentController = new CommentController(commentService);
    }

    @Test
    public void testGetAllComments() {
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment());
        when(commentService.getAllComments()).thenReturn(comments);

        ResponseEntity<List<Comment>> response = commentController.getAllComments();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(comments, response.getBody());
    }

    @Test
    public void testGetCommentById() {
        Comment comment = new Comment();
        when(commentService.getcommentById(1)).thenReturn(comment);

        ResponseEntity<Comment> response = commentController.getcommentById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(comment, response.getBody());
    }

    @Test
    public void testGetCommentByIdNotFound() {
        when(commentService.getcommentById(1)).thenReturn(null);

        ResponseEntity<Comment> response = commentController.getcommentById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testSaveComment() {
        CreatecommentRequest request = new CreatecommentRequest();
        when(commentService.saveComment(request)).thenReturn("Comment Created");

        ResponseEntity<String> response = commentController.saveComment(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Comment Created", response.getBody());
    }

//    @Test
//    public void testDeleteComment() {
//        commentController.deleteComment(1);
//        verify(commentService, times(1)).deleteComment(1);
//    }
}
