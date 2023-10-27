package blog.example.BlogApplication2;
import blog.example.BlogApplication2.Model.User;
import blog.example.BlogApplication2.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;

//@DataJpaTest
//public class UserRepositoryTest {
//
//
//    private final UserRepository userRepository;
//@Autowired
//    public UserRepositoryTest(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Test
//    public void testUserExistsInDatabase() {
//        User user = new User();
//        user.setName("Ajay");
//        user.setEmail("Ajay@gmail.com");
//        user.setPassword("7678907886");
//        userRepository.save(user);
//
//        boolean userExists = userRepository.findByEmail("Ajay@gmail.com").isPresent();
//        assertTrue(userExists);
//    }
//}



