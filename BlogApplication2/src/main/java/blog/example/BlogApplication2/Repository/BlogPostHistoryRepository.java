package blog.example.BlogApplication2.Repository;

import blog.example.BlogApplication2.Model.BlogPostHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogPostHistoryRepository extends JpaRepository<BlogPostHistory,Integer> {

    @Query(value = "select* from history where post_id=:postid",nativeQuery = true)
    List<BlogPostHistory>findByPostId(Integer postid);
}
