package blog.example.BlogApplication2.Service;
import blog.example.BlogApplication2.Model.*;
import blog.example.BlogApplication2.Repository.CommunityMappingRepository;
import blog.example.BlogApplication2.Repository.CommunityRepository;
import blog.example.BlogApplication2.Repository.PostRepository;
import blog.example.BlogApplication2.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final CommunityMappingRepository communityMappingRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostService postService;
@Autowired
    public CommunityService(CommunityRepository communityRepository, CommunityMappingRepository communityMappingRepository, UserRepository userRepository, PostRepository postRepository,PostService postService) {
        this.communityRepository = communityRepository;
        this.communityMappingRepository = communityMappingRepository;
        this.userRepository = userRepository;
        this.postRepository=postRepository;
        this.postService=postService;
    }


    public List<Community>getAllCommunities(){
    return communityRepository.findAll();
    }

    public Optional<Community> getCommunityById(Integer communityid) {
        return communityRepository.findById(communityid);
    }
    public String createCommunity(CreateCommunityRequest createCommunityRequest) {
        if (createCommunityRequest.getCommunityname() != null && !createCommunityRequest.getCommunityname().isEmpty()) {
            if (communityRepository.existsByCommunityname(createCommunityRequest.getCommunityname())!=0) {
                return "Community with the same name already exists!";
            } else {
                Community community = new Community();
                community.setCommunityname(createCommunityRequest.getCommunityname());
                community.setDescription(createCommunityRequest.getDescription());
                community.setMembercount(0);
                communityRepository.save(community);
                Communitymapping communitymapping=new Communitymapping();
                User user = userRepository.findById(createCommunityRequest.getUserid()).orElse(null);
                communitymapping.setUser(user);
                communitymapping.setCommunity(community);
                communityMappingRepository.save(communitymapping);
                return "Community created!";
            }
        } else {
            return "Community name cannot be empty!";
        }
    }

    public String joinCommunity(Integer userid, Integer communityid){
        Integer isMember = communityMappingRepository.existsByUserIdAndCommunityId(userid, communityid);
        if (isMember==0) {
            Communitymapping communityMapping = new Communitymapping();
            User user=userRepository.findById(userid).orElse(null);
            communityMapping.setUser(user);
            Community community=communityRepository.findById(communityid).orElse(null);
            communityMapping.setCommunity(community);
            community.setMembercount(community.getMembercount() + 1);
            communityRepository.save(community);
            communityMappingRepository.save(communityMapping);
            return "user joined:)";
        }
        else{
            return "user already joined!!";
        }
    }
    public Integer getAllUserJoinedCommunity(Integer userid, Integer communityid){
    return communityMappingRepository.findAllByUseridAndCommunityid(userid,communityid);
    }


    public List<Blogpost> getAllPostbyCommunityId(Integer communityid){
    return postService.getAllPostsByCommunityId(communityid);
    }
public String unjoinCommunity(Integer userid,Integer communityid){
    Integer isMember=communityMappingRepository.existsByUserIdAndCommunityId(userid,communityid);
    if(isMember==1){
       communityMappingRepository.deleteByUserIdAndCommunityId(userid,communityid);
       Community community=communityRepository.findById(communityid).orElse(null);
       if(community!=null){
           community.setMembercount(community.getMembercount()-1);
           communityRepository.save(community);
       }
        return  "User unjoined the community!!";
    }
    else {
        return "User is not a member of the Community";
    }
}
    public String deleteCommunity(Integer communityId) {
        if (communityRepository.existsById(communityId)) {
            communityRepository.deleteById(communityId);
            communityMappingRepository.deleteByCommunityId(communityId);
            return "Community deleted successfully";
        } else {
            return "Community not found";
        }
    }
    }


