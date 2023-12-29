package blog.example.BlogApplication2.Controller;

import blog.example.BlogApplication2.Model.Blogpost;
import blog.example.BlogApplication2.Model.ProfileResponse;
import blog.example.BlogApplication2.Model.ProfileUpdateRequest;
import blog.example.BlogApplication2.Model.User;
import blog.example.BlogApplication2.Service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/userProfile")
    public Optional<User> getProfileDetails(){
        return profileService.getUserProfile(SecurityContextHolder.getContext().getAuthentication().getName());

    }
    @GetMapping("/userbyId/{userid}")
    public ResponseEntity<User>getUserById(@PathVariable Integer userid){
        User user=profileService.getUserById(userid);
        if (user!=null){
            return  new ResponseEntity<>(user, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/{userid}/blogposts")
    public ResponseEntity<List<Blogpost>> getAllPostById(@PathVariable Integer userid){
        List<Blogpost> blogposts=profileService.getAllPostById(userid);
        if(blogposts!=null){
            return new ResponseEntity<>(blogposts,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
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
    @PostMapping(path="/update/{userid}")
    public ResponseEntity<ProfileResponse> updateUserProfile(@PathVariable Integer userid, @RequestBody ProfileUpdateRequest profileUpdateRequest){
        String newUsername=profileUpdateRequest.getNewname();
        String newBio=profileUpdateRequest.getNewBio();
        MultipartFile newProfilePicture=profileUpdateRequest.getNewProfilePicture();
        Optional<User> updatedUser=profileService.updateProfile(userid,newUsername,newBio,newProfilePicture);
        if (updatedUser.isPresent()){
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
