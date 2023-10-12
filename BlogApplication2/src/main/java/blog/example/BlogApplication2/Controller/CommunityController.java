package blog.example.BlogApplication2.Controller;

import blog.example.BlogApplication2.Service.CommunityService;
import blog.example.BlogApplication2.Model.Community;
import blog.example.BlogApplication2.Model.CreateCommunityRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/community")
public class CommunityController {
    private final CommunityService communityService;

    @Autowired
    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Community>> getAllCommunities() {
        List<Community> communities = communityService.getAllCommunities();
        return ResponseEntity.ok(communities);
    }
    @PostMapping("/create")
    public ResponseEntity<String> createCommunity(@RequestBody CreateCommunityRequest request) {
        String createdCommunity = communityService.createCommunity(request);
        return ResponseEntity.ok(createdCommunity);
    }
    @PostMapping("/join/{userId}/{communityId}")
    public ResponseEntity<String> joinCommunity(@PathVariable(name = "userId") Integer userId, @PathVariable(name = "communityId") Integer communityId) {
        return new ResponseEntity<>(communityService.joinCommunity(userId, communityId), HttpStatus.OK);
    }
@PostMapping("/unjoin/{userid}/{communityid}")
    public ResponseEntity<String> unjoinCommunity(@PathVariable(name = "userid") Integer userid,@PathVariable(name = "communityid")Integer communityid){
        return  new ResponseEntity<>(communityService.unjoinCommunity(userid,communityid),HttpStatus.OK);
}
}
