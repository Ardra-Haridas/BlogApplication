package blog.example.BlogApplication2.Service;

import blog.example.BlogApplication2.Model.ProfileResponse;
import blog.example.BlogApplication2.Model.User;
import blog.example.BlogApplication2.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    @Autowired
    private final UserRepository repository;

    public ProfileResponse getUserProfile(String userEmail) {
        Optional<User> optionalUser = repository.findByEmail(userEmail);

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
}
