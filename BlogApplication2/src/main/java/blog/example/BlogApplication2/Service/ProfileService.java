package blog.example.BlogApplication2.Service;
import blog.example.BlogApplication2.Model.ProfileResponse;
import blog.example.BlogApplication2.Model.User;
import blog.example.BlogApplication2.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final ProfileUploadService profileUploadService;


    public ProfileResponse getUserProfile(String userEmail) {
        Optional<User> optionalUser = userRepository.findByEmail(userEmail);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return ProfileResponse.builder()
                    .username(user.getUsername())
                    .bio(user.getBio())
                    .profilepicture(user.getProfilepicture())
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
    
    public void uploadImage(Integer userid, MultipartFile imageFile) {
       profileUploadService.uploadImage(userid, imageFile);
    }
    public byte[] getImageRelativePath(Integer userid) throws IOException {
        User user= userRepository.findById(userid).orElse(null);
        String filepath="/Users/ardra.h/Downloads/BlogApplication2/BlogApplication2/src/main/java/blog/example/BlogApplication2/Images/"+userid+"/"+user.getProfilepicture();
        byte[]  image = Files.readAllBytes(new File(filepath).toPath());
        return image;
    }

}

