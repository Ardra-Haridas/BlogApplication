package blog.example.BlogApplication2.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BlogPostRequest {

    private String title;
    private String content;
    private  Integer communityid;
    private Integer userid;
}
