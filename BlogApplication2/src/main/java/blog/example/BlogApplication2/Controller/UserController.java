package blog.example.BlogApplication2.Controller;

import blog.example.BlogApplication2.Repository.UserRepository;
import blog.example.BlogApplication2.Service.ProfileService;
import blog.example.BlogApplication2.Model.ProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/userProfile")
    public ProfileResponse getProfileDetails(){
        return profileService.getUserProfile(SecurityContextHolder.getContext().getAuthentication().getName());

    }
    @PostMapping(path = "/uploadImage/{userid}")
    public ResponseEntity<String> uploadImage(@PathVariable Integer userid, @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            profileService.uploadImage(userid, imageFile);
            return ResponseEntity.ok("Image uploaded successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to upload image: " + e.getMessage());
        }
    }

    @GetMapping(path = "/getImage/{userid}")
    public ResponseEntity<?> getImageRelativePath(@PathVariable Integer userid) throws IOException {
        byte[] imageData = profileService.getImageRelativePath(userid);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(imageData);
    }

}
