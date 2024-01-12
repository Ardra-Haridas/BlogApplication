package blog.example.BlogApplication2.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommunityRequest {

    private String communityname;
    private String description;
    private String profilepic;
    private Integer userid;
    private Integer membercount;
}
