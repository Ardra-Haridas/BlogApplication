package blog.example.BlogApplication2.Controller;

import blog.example.BlogApplication2.Service.CommentService;
import blog.example.BlogApplication2.Model.Comment;
import blog.example.BlogApplication2.Model.CreatecommentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/comment")
public class CommentController {

private final CommentService commentService;
@Autowired
public CommentController(CommentService commentService){

    this.commentService=commentService;
}
@GetMapping("/allComments")
    public ResponseEntity<List<Comment>>getAllComments(){
    List<Comment> comments= commentService.getAllComments();
    return  ResponseEntity.ok(comments);
}
@GetMapping("/findbyId")
    public ResponseEntity<Comment>getcommentById(@PathVariable Integer commentid){
    Comment comment=commentService.getcommentById(commentid);
    if (comment!=null){
        return  new ResponseEntity<>(comment, HttpStatus.OK);
    }
    else
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
}
@PostMapping("/createComment")
    public ResponseEntity<String>saveComment(@RequestBody CreatecommentRequest request){
    return ResponseEntity.ok(commentService.saveComment(request));
}
@DeleteMapping("/deleteComment/{commentid}")
public void deleteComment(@PathVariable Integer commentid) {
    commentService.deleteComment(commentid);
}
}
