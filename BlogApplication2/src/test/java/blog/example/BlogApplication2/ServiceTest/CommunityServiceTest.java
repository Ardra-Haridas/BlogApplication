package blog.example.BlogApplication2.ServiceTest;
import blog.example.BlogApplication2.Model.Community;
import blog.example.BlogApplication2.Model.Communitymapping;
import blog.example.BlogApplication2.Model.CreateCommunityRequest;
import blog.example.BlogApplication2.Model.User;
import blog.example.BlogApplication2.Repository.CommunityMappingRepository;
import blog.example.BlogApplication2.Repository.CommunityRepository;
import blog.example.BlogApplication2.Repository.UserRepository;
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
    private CommunityRepository communityRepository;
    @Mock
    private CommunityMappingRepository communityMappingRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private CommunityService communityService;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testCreateCommunity() {
        CreateCommunityRequest request = new CreateCommunityRequest();
        request.setCommunityname("TestCommunity");
        request.setUserid(1);
        User user = new User();
        user.setId(1);
        Community community = new Community();
        community.setCommunityname("TestCommunity");
        Mockito.when(communityRepository.existsByCommunityname("TestCommunity")).thenReturn(0);
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
        Mockito.when(communityRepository.save(Mockito.any(Community.class))).thenReturn(community);
        Mockito.when(communityMappingRepository.save(Mockito.any(Communitymapping.class))).thenReturn(new Communitymapping());
        String result = communityService.createCommunity(request);
        assertEquals("Community created!", result);
    }

    }




