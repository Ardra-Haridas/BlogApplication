package blog.example.BlogApplication2.Repository;

import blog.example.BlogApplication2.Model.Blogpost;
import blog.example.BlogApplication2.Model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {
    @Query(value = "Select * from comments where postid=?",nativeQuery = true)
    List<Comment> findByBlogpost(Integer postid);
}
