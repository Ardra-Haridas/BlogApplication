package blog.example.BlogApplication2.Controller;

import blog.example.BlogApplication2.Model.BlogpostHistory;
import blog.example.BlogApplication2.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@RestController
//@RequestMapping("/api/v1/history")
//public class HistoryController {
//
//  private final PostService postService;
//@Autowired
//    public HistoryController(PostService postService) {
//        this.postService = postService;
//    }
//
//    @GetMapping("/{postid}")
//    public ResponseEntity<List<BlogpostHistory>>getBlogpostHistory(@PathVariable Integer postid){
//    List<BlogpostHistory> historyList=postService.
//    }
//}
