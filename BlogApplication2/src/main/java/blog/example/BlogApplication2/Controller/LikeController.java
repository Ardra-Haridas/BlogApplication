package blog.example.BlogApplication2.Controller;

import blog.example.BlogApplication2.Model.Likes;
import blog.example.BlogApplication2.Service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/like")
public class LikeController {
    private final LikeService likeService;
@Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/likePost/{postid}/{userid}")
    public ResponseEntity<Object>likePost(@PathVariable(name = "postid") Integer postid, @PathVariable(name = "userid") Integer userid){
    return  ResponseEntity.ok(likeService.likePost(postid,userid));
    }

    @PostMapping("/unlikePost/{postid}/{userid}")
    public ResponseEntity<String>unlikePost(@PathVariable(name = "postid") Integer postid,@PathVariable(name = "userid") Integer userid){
    String result=likeService.unlikePost(postid,userid);
    return ResponseEntity.ok(result);
    }
    @GetMapping("/likebyuser/{userid}/{postid}")
    public ResponseEntity<Integer> getAllLikesByUserAndPost(@PathVariable Integer userid, @PathVariable Integer postid){
    return new ResponseEntity<>(likeService.getAllLikesByUserandPost(userid,postid), HttpStatus.OK);
    }
    @PostMapping("/likeComment/{commentid}/{userid}")
    public ResponseEntity<String>likeComment(@PathVariable(name = "commentid") Integer commentid,@PathVariable(name = "userid") Integer userid){
    String result=likeService.likeComment(commentid,userid);
    return  ResponseEntity.ok(result);
    }

    @PostMapping("/unlikeComment/{commentid}/{userid}")
    public ResponseEntity<String>unlikeComment(@PathVariable(name = "commentid")Integer commentid,@PathVariable(name = "userid") Integer userid){
    String result= likeService.unlikeComment(commentid);
    return ResponseEntity.ok(result);
    }

}
