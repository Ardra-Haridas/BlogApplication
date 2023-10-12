package blog.example.BlogApplication2.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ImageUploadService {

    @Value("${image.upload.directory}")
    private String uploadDirectory;

    public void uploadImage(Integer postId, MultipartFile imageFile) {
        String filePath = uploadDirectory + File.separator + postId + File.separator;

        try {
            File directory = new File(filePath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File file = new File(filePath + imageFile.getOriginalFilename());
            imageFile.transferTo(file);
            String relativePath = postId + "/" + imageFile.getOriginalFilename();

        } catch (IOException e) {
        }
    }
}
