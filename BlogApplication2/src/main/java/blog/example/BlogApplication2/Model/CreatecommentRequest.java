package blog.example.BlogApplication2.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatecommentRequest {
private  Integer userid;
private Integer postid;
private  Integer parentcommentid;
private String content;
private Date creationdate;
private Date lastmodifieddate;


}
