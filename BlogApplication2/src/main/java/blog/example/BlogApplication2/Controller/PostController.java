package blog.example.BlogApplication2.Controller;

import blog.example.BlogApplication2.Service.PostService;
import blog.example.BlogApplication2.Model.Blogpost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {
    @Autowired
    private PostService postService;
    public PostController(PostService postService){
        this.postService=postService;
    }
    @GetMapping(path = "/blogs")
    public ResponseEntity<List<Blogpost>>getAllBlogposts(){
        List<Blogpost> blogposts = postService.getAllPosts();
        return ResponseEntity.ok(blogposts);
    }
    @PostMapping(path ="/createpost")
    public ResponseEntity<Blogpost>createBlogpost(@RequestBody Blogpost blogpost){
        Blogpost createdPost=postService.createBlogpost(blogpost);
        return ResponseEntity.ok(createdPost);
    }

    @PostMapping(path = "/update/{postid}")

    public ResponseEntity<?>updateBlogpost(
            @PathVariable Integer postid,
            @RequestBody Blogpost updatedBlogpost){
        Blogpost updatePost=postService.updateBlogpost(postid,updatedBlogpost);
        if(updatePost!= null){
            return ResponseEntity.ok("Post Updated Successfully");

        }else{
            return ResponseEntity.badRequest().body("Failed to update post");
        }
    }

//    public ResponseEntity<Blogpost> updateBlogpost(
//            @PathVariable Integer postid,
//            @RequestBody Blogpost updatedBlogpost) {
//
//        Blogpost existingPost = postService.getPostById(postid);
//
//        if (existingPost == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        existingPost.setContent(updatedBlogpost.getContent());
//        existingPost.setLastmodifieddate(new Date());
//        Blogpost updatedPost = postService.updateBlogpost(existingPost);
//
//        return ResponseEntity.ok(updatedPost);
//    }

    @DeleteMapping(path = "/delete/{postid}")
    public ResponseEntity<String>deleteBlogpost(@PathVariable Integer postid){
        boolean deleted= postService.deleteBlogpost(postid);
        if(deleted){
            return  ResponseEntity.ok("Post Deleted Successfully");
        }else
        {
            return  ResponseEntity.badRequest().body("Failed to delete post");
        }
    }

}
