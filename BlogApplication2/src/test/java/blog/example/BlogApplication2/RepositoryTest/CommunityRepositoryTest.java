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
    public void existsByCommunityname() {
        Community community=new Community();
        community.setCommunityname("Community91");
        communityRepository.save(community);
        Integer count=communityRepository.existsByCommunityname("Community91");
        assertEquals(2,count);
    }

    @Test
    public void CommunitynotExist(){
        Integer count=communityRepository.existsByCommunityname("NoExistentCommunity");
        assertEquals(0,count);
    }
}