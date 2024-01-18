package blog.example.BlogApplication2.Controller;

import blog.example.BlogApplication2.Model.Blogpost;
import blog.example.BlogApplication2.Service.CommentService;
import blog.example.BlogApplication2.Model.Comment;
import blog.example.BlogApplication2.Model.CreatecommentRequest;
import blog.example.BlogApplication2.Util.AppContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/commentsByPostId/{postid}")
    public ResponseEntity<List<Map<String,Object>>> getCommentsByPostId(@PathVariable Integer postid) {
        List<Map<String,Object>> comments = commentService.getCommentsByPostId(postid);
        if (comments !=null) {
            return ResponseEntity.ok(comments);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
@GetMapping("/getContext")
    public ResponseEntity<String>getContext(){
        return  new ResponseEntity<>(AppContext.getEmail(), HttpStatus.OK);
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
public ResponseEntity<String> deleteComment(@PathVariable Integer commentid)throws Exception{
    commentService.deleteComment(commentid);
    return ResponseEntity.badRequest().body("delete comment");
}

    @PostMapping(path = "/update/{commentid}")
    public ResponseEntity<?> updateComment(@PathVariable Integer commentid, @RequestBody Comment updatedComment) {
        Comment updateComment= commentService.updateComment(commentid, updatedComment);
        if (updateComment != null) {
            return ResponseEntity.ok("Comment Updated Successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to update comment");
        }
    }

}



