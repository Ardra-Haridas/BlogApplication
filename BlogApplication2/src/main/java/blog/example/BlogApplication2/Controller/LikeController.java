package blog.example.BlogApplication2.Controller;

import blog.example.BlogApplication2.Service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/like")
public class LikeController {
    private final LikeService likeService;
@Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/likePost/{postid}/{userid}")
    public ResponseEntity<String>likePost(@PathVariable(name = "postid") Integer postid, @PathVariable(name = "userid") Integer userid){
    String result =likeService.likePost(postid,userid);
    return  ResponseEntity.ok(result);
    }

    @PostMapping("/unlikePost/{postid}/{userid}")
    public ResponseEntity<String>unlikePost(@PathVariable(name = "postid") Integer postid,@PathVariable(name = "userid") Integer userid){
    String result=likeService.unlikePost(postid,userid);
    return ResponseEntity.ok(result);
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
