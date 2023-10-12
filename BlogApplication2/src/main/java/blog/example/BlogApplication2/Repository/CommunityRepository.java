package blog.example.BlogApplication2.Repository;

import blog.example.BlogApplication2.Model.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRepository extends JpaRepository<Community,Integer> {

  @Query(value = "select count(*) from community where communityname=?",nativeQuery = true)
  Integer existsByCommunityname(String communityname);
}
