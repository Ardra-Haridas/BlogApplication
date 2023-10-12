package blog.example.BlogApplication2.Controller;

import blog.example.BlogApplication2.Service.ProfileService;
import blog.example.BlogApplication2.Model.ProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/userProfile")
    public ProfileResponse getProfileDetails(){
        return profileService.getUserProfile(SecurityContextHolder.getContext().getAuthentication().getName());

    }

}
