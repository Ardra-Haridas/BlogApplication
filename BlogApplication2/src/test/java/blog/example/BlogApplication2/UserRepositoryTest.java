package blog.example.BlogApplication2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import blog.example.BlogApplication2.Model.User;
import blog.example.BlogApplication2.Repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;

@DataJpaTest
public class UserRepositoryTest {


    private final UserRepository userRepository;
@Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    public void checkIfUserExistsById() {
        Optional<User> userOptional = userRepository.findById(1702);

        assertThat(userOptional).isPresent();
    }
}
