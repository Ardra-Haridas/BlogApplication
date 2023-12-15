package blog.example.BlogApplication2.RepositoryTest;
import blog.example.BlogApplication2.Model.Community;
import blog.example.BlogApplication2.Repository.CommunityRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CommunityRepositoryTest {
 @Autowired
    private CommunityRepository communityRepository;


    @Test
    public void saveCommunityTest() {
        Community community=new Community();
        community.setCommunityname("Community91");
        communityRepository.save(community);

        Assertions.assertThat(community.getCommunityid()).isGreaterThan(0);
    }
    @Test
    public void CommunitynotExist(){
        Integer count=communityRepository.existsByCommunityname("NoExistentCommunity");
        assertEquals(0,count);
    }
   @Test
    public void testCommunityExistByCommunityName(){
        Community community=new Community();
        community.setCommunityname("TestCommunity");
        communityRepository.save(community);
        Integer count =communityRepository.existsByCommunityname("TestCommunity");
        assertEquals(1,count);
    }
    @Test
    public void testCommunityExistByCommunityId(){
        Community community=new Community();
         community.setCommunityname("TestCommunity");
         communityRepository.save(community);
         Integer count= communityRepository.existsByCommunityId(community.getCommunityid());
         assertEquals(1,count);
    }
}