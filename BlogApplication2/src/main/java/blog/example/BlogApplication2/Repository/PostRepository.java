package blog.example.BlogApplication2.Repository;

import blog.example.BlogApplication2.Model.Blogpost;
import blog.example.BlogApplication2.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Blogpost,Integer> {

    Optional<Blogpost> findById(Integer postid);

    @Query(value = "SELECT * from blogpost  where userid=?1",nativeQuery = true)
    List<Blogpost> findAllById(Integer userid);

    @Query(value = "SELECT * from blogpost  where communityid=?1",nativeQuery = true)
    List<Blogpost> findByCommunityId(Integer communityid);
}

