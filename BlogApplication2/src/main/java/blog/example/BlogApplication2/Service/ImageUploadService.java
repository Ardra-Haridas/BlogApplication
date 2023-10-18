package blog.example.BlogApplication2.Service;
import blog.example.BlogApplication2.Model.Blogpost;
import blog.example.BlogApplication2.Model.ProfileResponse;
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
import java.util.UUID;

@Service
public class ImageUploadService {

    private final PostRepository postRepository;



    @Value("${image.upload.directory}")
    private String uploadDirectory;
    @Autowired
    public ImageUploadService(PostRepository postRepository) {
        this.postRepository = postRepository;

    }

    public void uploadImage(Integer postId, MultipartFile imageFile) {
        String filePath = uploadDirectory + "/"+ postId + "/";
        Blogpost post = postRepository.findById(postId).orElse(null);
        if(post!=null){
            try {
                File directory = new File(filePath);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                String originalFilename = postId+imageFile.getOriginalFilename();
                post.setImage(originalFilename);
                postRepository.save(post);
                Path path = Paths.get(filePath, originalFilename);
                byte[] bytes = imageFile.getBytes();
                Files.write(path, bytes);

                String relativePath = postId + "/" + originalFilename;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
