package blog.example.BlogApplication2.Repository;

import blog.example.BlogApplication2.Model.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History,Integer> {



    @Query(value = "select* from history where post_id=:postid",nativeQuery = true)
    List<History>findByPostId(Integer postid);
}
