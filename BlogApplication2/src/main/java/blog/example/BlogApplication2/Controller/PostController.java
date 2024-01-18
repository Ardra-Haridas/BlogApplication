package blog.example.BlogApplication2.Controller;
import blog.example.BlogApplication2.Model.BlogPostRequest;
import blog.example.BlogApplication2.Service.PostService;
import blog.example.BlogApplication2.Model.Blogpost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    @GetMapping("/findbyId/{postid}")
    public ResponseEntity<Blogpost>getpostById(@PathVariable Integer postid){
        Blogpost blogpost=postService.getpostById(postid);
        if (blogpost!=null){
            return  new ResponseEntity<>(blogpost, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/createpost")
    public ResponseEntity<Blogpost> createBlogpost(@RequestBody BlogPostRequest blogPostRequest) {
        Blogpost createdPost = postService.createBlogpost(blogPostRequest);
        return ResponseEntity.ok(createdPost);
    }

    @PostMapping(path = "/update/{postid}")
    public Blogpost updateBlogpost(@PathVariable Integer postid, @RequestBody Blogpost updatedBlogpost) {
        Blogpost updatePost = postService.updateBlogpost(postid, updatedBlogpost);
        return updatePost;
    }

    @DeleteMapping(path = "/delete/{postid}")
    public ResponseEntity<String> deleteBlogpost(@PathVariable Integer postid) throws Exception {
        postService.deleteBlogpost(postid);
        return ResponseEntity.badRequest().body("delete post");
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
    public ResponseEntity<?> getImageRelativePath(@PathVariable Integer postid) throws IOException {
         byte[] imageData = postService.getImageRelativePath(postid);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(imageData);
    }

    }


