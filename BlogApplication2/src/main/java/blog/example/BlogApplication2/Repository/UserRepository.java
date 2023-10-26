package blog.example.BlogApplication2.Repository;

import blog.example.BlogApplication2.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String email);
@Query(value = "select * from user where name=?1 or email=?2",nativeQuery = true)
List<User> duplicateExist(String username, String email);
}
