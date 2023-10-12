package blog.example.BlogApplication2.Repository;

import blog.example.BlogApplication2.Model.Blogpost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Blogpost,Integer> {

    Optional<Blogpost> findById(Integer postid);
}
