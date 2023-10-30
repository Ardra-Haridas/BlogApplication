package blog.example.BlogApplication2.Repository;

import blog.example.BlogApplication2.Model.Community;
import blog.example.BlogApplication2.Model.Communitymapping;
import blog.example.BlogApplication2.Model.CreateCommunityRequest;
import blog.example.BlogApplication2.Model.User;
import blog.example.BlogApplication2.Service.CommunityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CommunityServiceTest {
    @Mock
    private CommunityMappingRepository communityMappingRepository;
    @Mock
    private CommunityRepository communityRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private CommunityService communityService;
    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public  void  testCreateCommunity_Success(){
        CreateCommunityRequest request=new CreateCommunityRequest();
        request.setCommunityname("TestCommunity");
        request.setUserid(302);
        User user=new User();
        user.setId(302);
        Community community=new Community();
        Mockito.when(communityRepository.existsByCommunityname("TestCommunity")).thenReturn(0);
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
        String result=communityService.createCommunity(request);
        assertEquals("Community created!",result);
    }
    @Test
    public  void testCreateCommunity_DuplicateName(){
        CreateCommunityRequest createCommunityRequest=new CreateCommunityRequest();
        createCommunityRequest.setCommunityname("TestCommunity");
        createCommunityRequest.setUserid(302);
        Mockito.when(communityRepository.existsByCommunityname(createCommunityRequest.getCommunityname())).thenReturn(19);
        String result=communityService.createCommunity(createCommunityRequest);
        assertEquals("Community with the same name already exists!",result);
    }
}
