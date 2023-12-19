package blog.example.BlogApplication2.Service;
import blog.example.BlogApplication2.Model.Blogpost;
import blog.example.BlogApplication2.Model.User;
import blog.example.BlogApplication2.Repository.PostRepository;
import blog.example.BlogApplication2.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final ProfileUploadService profileUploadService;


    public Optional<User> getUserProfile(String userEmail) {
        Optional<User> optionalUser = userRepository.findByEmail(userEmail);


        if (optionalUser.isPresent()) {
            optionalUser.get().setPassword(null);
            return optionalUser;
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
    public List<Blogpost> getAllPostById(Integer userid){
        return postRepository.findAllById(userid);
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
    @Transactional
    public Optional<User>updateProfile(Integer userid,String newname, String newBio,MultipartFile newProfilepicture){
        User user=userRepository.findById(userid).orElseThrow(()->new UsernameNotFoundException("User not fund"));
        if(newname!=null){
            user.setName(newname);
        }
        if (newBio!=null){
            user.setBio(newBio);
        }
        if (newProfilepicture!=null && !newProfilepicture.isEmpty()){
            String newProfilePictureFilename= profileUploadService.uploadImage(userid,newProfilepicture);
            user.setProfilepicture(newProfilePictureFilename);
        }
        userRepository.save(user);
        return Optional.of(user);
    }



}

