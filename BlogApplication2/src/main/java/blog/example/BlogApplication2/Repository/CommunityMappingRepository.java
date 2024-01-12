package blog.example.BlogApplication2.Repository;

import blog.example.BlogApplication2.Model.Communitymapping;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityMappingRepository extends JpaRepository<Communitymapping,Integer> {
    @Query(value = "select count(*) from communityusermapping where userid = ?1 and communityid = ?2",nativeQuery = true)
    Integer existsByUserIdAndCommunityId(Integer userid, Integer communityid);

    @Transactional
    @Modifying
    @Query(value = "delete from communityusermapping where userid=?1 and communityid=?2",nativeQuery = true)
    void deleteByUserIdAndCommunityId(Integer userid, Integer communityid);


        @Transactional
        @Modifying
        @Query(value = "delete from communityusermapping where communityid = ?1", nativeQuery = true)
        void deleteByCommunityId(Integer communityId);

    @Query(value="select count(*) from communityusermapping where userid=?1 and communityid=?2 ",nativeQuery = true)
     Integer findAllByUseridAndCommunityid(Integer userid, Integer communityid);
    }


