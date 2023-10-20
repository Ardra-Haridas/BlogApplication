package blog.example.BlogApplication2.Service;

import blog.example.BlogApplication2.Model.Blogpost;
import blog.example.BlogApplication2.Model.User;
import blog.example.BlogApplication2.Repository.PostRepository;
import blog.example.BlogApplication2.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ProfileUploadService {
    @Value("${image.upload.directory}")
    private String uploadDirectory;
    private final UserRepository userRepository;
@Autowired
    public ProfileUploadService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void uploadImage(Integer userid, MultipartFile imageFile) {
        String filePath = uploadDirectory + "/"+ userid + "/";
       User user = userRepository.findById(userid).orElse(null);
        if(user!=null){
            try {
                File directory = new File(filePath);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                String originalFilename = userid+imageFile.getOriginalFilename();
                user.setProfilepicture(originalFilename);
                userRepository.save(user);
                Path path = Paths.get(filePath, originalFilename);
                byte[] bytes = imageFile.getBytes();
                Files.write(path, bytes);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
