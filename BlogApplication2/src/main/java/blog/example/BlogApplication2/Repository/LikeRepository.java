package blog.example.BlogApplication2.Repository;

import blog.example.BlogApplication2.Model.Likes;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface LikeRepository extends JpaRepository<Likes,Integer> {
    @Query(value = "SELECT count(*) FROM Likes as l WHERE l.postid = ?1 AND l.userid = ?2",nativeQuery = true)
    Integer existsByPostIdAndUserId(Integer postid, Integer userid);
    @Query(value = "SELECT count(*) FROM Likes as l WHERE l.commentid = ?1 AND l.userid = ?2",nativeQuery = true)
   Integer existsByCommentIdAndUserId(Integer commentid);
    @Modifying
    @Query(value = "DELETE FROM Likes l WHERE l.postId = ?1",nativeQuery = true)
    void deleteByPostId(Integer postid);
@Modifying
@Transactional
@Query(value = "DELETE FROM Likes l where l.postid=?1 AND l.userid=?2",nativeQuery = true)
    void deleteByPostIdAndUserId(Integer postid, Integer userid);
@Modifying
@Transactional
@Query(value = "Delete from Likes l where l.commentid=?1",nativeQuery = true)
void deleteByCommentId(Integer commentid);
//    void deleteByCommentIdAndUserId(Integer commentid, Integer userid);


}
