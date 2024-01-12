package blog.example.BlogApplication2.Controller;

import blog.example.BlogApplication2.Model.Blogpost;
import blog.example.BlogApplication2.Model.User;
import blog.example.BlogApplication2.Service.CommunityService;
import blog.example.BlogApplication2.Model.Community;
import blog.example.BlogApplication2.Model.CreateCommunityRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/community")
public class CommunityController {
    private final CommunityService communityService;

    @Autowired
    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }



    @PostMapping("/create")
    public ResponseEntity<String> createCommunity(@RequestBody CreateCommunityRequest request) {
        String createdCommunity = communityService.createCommunity(request);
        return ResponseEntity.ok(createdCommunity);
    }
    @GetMapping("/list")
    public ResponseEntity<List<Community>> getAllCommunities(){
        List<Community> communities=communityService.getAllCommunities();
        return ResponseEntity.ok(communities);
    }
    @GetMapping("/communitybyId/{communityId}")
    public ResponseEntity<Community> getCommunityById(@PathVariable Integer communityId) {
        Optional<Community> communityOptional = communityService.getCommunityById(communityId);

        if (communityOptional.isPresent()) {
            return ResponseEntity.ok(communityOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/join/{userId}/{communityId}")
    public ResponseEntity<String> joinCommunity(@PathVariable(name = "userId") Integer userId, @PathVariable(name = "communityId") Integer communityId) {
        return new ResponseEntity<>(communityService.joinCommunity(userId, communityId), HttpStatus.OK);
    }

    @GetMapping("/joineduser/{userid}/{communityid}")
    public ResponseEntity<Integer> getAllUserJoinedCommunity(@PathVariable Integer userid, @PathVariable Integer communityid){
        return new ResponseEntity<>(communityService.getAllUserJoinedCommunity(userid,communityid), HttpStatus.OK);
    }

    @GetMapping("/{communityid}/posts")
    public ResponseEntity<List<Blogpost>> getAllPostbyCommunityId(@PathVariable Integer communityid){
        List<Blogpost> posts=communityService.getAllPostbyCommunityId(communityid);
        return ResponseEntity.ok(posts);
    }
@PostMapping("/unjoin/{userid}/{communityid}")
    public ResponseEntity<String> unjoinCommunity(@PathVariable(name = "userid") Integer userid,@PathVariable(name = "communityid")Integer communityid){
        return  new ResponseEntity<>(communityService.unjoinCommunity(userid,communityid),HttpStatus.OK);
}
    @DeleteMapping("/delete/{communityId}")
    public ResponseEntity<String> deleteCommunity(@PathVariable(name = "communityId") Integer communityId) {
        String result = communityService.deleteCommunity(communityId);
        return ResponseEntity.ok(result);
    }


}
