package blog.example.BlogApplication2.Repository;

import blog.example.BlogApplication2.Model.Blogpost;
import blog.example.BlogApplication2.Model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {
    @Query(value = "select c.*,u.name,CASE WHEN l.userid = ?2 THEN true ELSE false END as isLiked from comments as c \n" +
            "\tleft join likes as l on c.commentid = l.commentid\n" +
            "    inner join user as u on u.id = c.userid\n" +
            "    where c.postid = ?1", nativeQuery = true)
    List<Map<String, Object>> findByBlogpost(Integer postid, Integer userid);

}
