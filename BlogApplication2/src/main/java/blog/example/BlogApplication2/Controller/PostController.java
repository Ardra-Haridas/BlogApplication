package blog.example.BlogApplication2.Controller;
import blog.example.BlogApplication2.Service.PostService;
import blog.example.BlogApplication2.Model.Blogpost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping(path = "/blogs")
    public ResponseEntity<List<Blogpost>> getAllBlogposts() {
        List<Blogpost> blogposts = postService.getAllPosts();
        return ResponseEntity.ok(blogposts);
    }

    @PostMapping(path = "/createpost")
    public ResponseEntity<Blogpost> createBlogpost(@RequestBody Blogpost blogpost) {
        Blogpost createdPost = postService.createBlogpost(blogpost);
        return ResponseEntity.ok(createdPost);
    }

    @PostMapping(path = "/update/{postid}")
    public ResponseEntity<?> updateBlogpost(@PathVariable Integer postid, @RequestBody Blogpost updatedBlogpost) {
        Blogpost updatePost = postService.updateBlogpost(postid, updatedBlogpost);
        if (updatePost != null) {
            return ResponseEntity.ok("Post Updated Successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to update post");
        }
    }

    @DeleteMapping(path = "/delete/{postid}")
    public ResponseEntity<String> deleteBlogpost(@PathVariable Integer postid) {
        boolean deleted = postService.deleteBlogpost(postid);
        if (deleted) {
            return ResponseEntity.ok("Post Deleted Successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to delete post");
        }
    }

    @PostMapping(path = "/uploadImage/{postid}")
    public ResponseEntity<String> uploadImage(@PathVariable Integer postid, @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            postService.uploadImage(postid, imageFile);
            return ResponseEntity.ok("Image uploaded successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to upload image: " + e.getMessage());
        }
    }

    @GetMapping(path = "/getImage/{postid}")
    public ResponseEntity<String> getImageRelativePath(@PathVariable Integer postid) {
        String relativePath = postService.getImageRelativePath(postid);
        if (relativePath != null) {
            return ResponseEntity.ok(relativePath);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
