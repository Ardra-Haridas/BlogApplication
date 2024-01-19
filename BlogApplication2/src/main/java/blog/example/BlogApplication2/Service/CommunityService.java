package blog.example.BlogApplication2.Service;
import blog.example.BlogApplication2.Model.*;
import blog.example.BlogApplication2.Repository.CommunityMappingRepository;
import blog.example.BlogApplication2.Repository.CommunityRepository;
import blog.example.BlogApplication2.Repository.PostRepository;
import blog.example.BlogApplication2.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final CommunityMappingRepository communityMappingRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostService postService;
    private final ImageUploadService imageUploadService;
@Autowired
    public CommunityService(CommunityRepository communityRepository, CommunityMappingRepository communityMappingRepository, UserRepository userRepository, PostRepository postRepository,PostService postService,ImageUploadService imageUploadService) {
        this.communityRepository = communityRepository;
        this.communityMappingRepository = communityMappingRepository;
        this.userRepository = userRepository;
        this.postRepository=postRepository;
        this.postService=postService;
        this.imageUploadService=imageUploadService;
    }


    public List<Community>getAllCommunities(){
    return communityRepository.findAll();
    }

    public Optional<Community> getCommunityById(Integer communityid) {
        return communityRepository.findById(communityid);
    }
    public Community createCommunity(CreateCommunityRequest createCommunityRequest) {
        if (createCommunityRequest.getCommunityname() != null && !createCommunityRequest.getCommunityname().isEmpty()) {
            if (communityRepository.existsByCommunityname(createCommunityRequest.getCommunityname())!=0) {
                throw new RuntimeException("Community with the same name already exists!");
            } else {
                Community community = new Community();
                community.setCommunityname(createCommunityRequest.getCommunityname());
                community.setDescription(createCommunityRequest.getDescription());
                community.setMembercount(0);
                Community community1 =  communityRepository.save(community);
                Communitymapping communitymapping=new Communitymapping();
                User user = userRepository.findById(createCommunityRequest.getUserid()).orElse(null);
                communitymapping.setUser(user);
                communitymapping.setCommunity(community1);
                communityMappingRepository.save(communitymapping);
                return community1;
            }
        } else {
            throw new RuntimeException("Community name cannot be empty!");
        }
    }
    public void communityImage(Integer communityid, MultipartFile imageFile) {
        imageUploadService.communityImage(communityid, imageFile);
    }
    public byte[] getImageRelativePath(Integer communityid) throws IOException {
        Community community=communityRepository.findById(communityid).orElse(null);
        String filepath= "/Users/ardra.h/Downloads/BlogApplication2/BlogApplication2/src/main/java/blog/example/BlogApplication2/Images/"+communityid+"/"+community.getProfilepic();
        byte[] image= Files.readAllBytes(new File(filepath).toPath());
        return image;
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
public List<Map<String,Object>> getAllCommunitiesForUser(Integer userid){
    return communityMappingRepository.findAllCommunitiesByUserId(userid);
}

public List<Map<String,Object>> getAllCommunitiesNotJoinedByUser(Integer userid){
    return communityMappingRepository.findAllCommunitiesNotByUserId(userid);
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
//public Optional<Community>updateCommunity(Integer newcommunityid,String newcommunityname, String newdescription,MultipartFile newProfilepicture){
//    User user=userRepository.findById(newcommunityid).orElseThrow(()->new UsernameNotFoundException("Community not fund"));
//    if(newcommunityname!=null){
//        user.setName(newcommunityname);
//    }
//    if (newdescription!=null){
//        user.setBio(newdescription);
//    }
//    if (newProfilepicture!=null && !newProfilepicture.isEmpty()){
//        String newProfilePictureFilename= profileUploadService.uploadImage(newcommunityid,newProfilepicture);
//        user.setProfilepicture(newProfilePictureFilename);
//    }
//    userRepository.save(user);
//    return Optional.of(user);
//}
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


