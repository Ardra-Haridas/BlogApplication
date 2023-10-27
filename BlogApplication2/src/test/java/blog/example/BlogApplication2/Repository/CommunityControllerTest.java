package blog.example.BlogApplication2.Repository;

import blog.example.BlogApplication2.Controller.CommunityController;
import blog.example.BlogApplication2.Model.Community;
import blog.example.BlogApplication2.Service.CommunityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CommunityControllerTest {

    private MockMvc mockMvc;
    @InjectMocks
    private CommunityController communityController;
    @Mock
    private CommunityService communityService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        mockMvc= MockMvcBuilders.standaloneSetup(communityController).build();
    }
    @Test
    public void testAllCommunities() throws Exception{
        Community community1=new Community();
        community1.setCommunityid(1);
        community1.setCommunityname("community1");

        Community community2=new Community();
        community2.setCommunityid(2);
        community2.setCommunityname("community2");

        List<Community>communities= Arrays.asList(community1,community2);
        when(communityService.getAllCommunities()).thenReturn(communities);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/community/list")).andExpect(status().isOk());
    }
}
