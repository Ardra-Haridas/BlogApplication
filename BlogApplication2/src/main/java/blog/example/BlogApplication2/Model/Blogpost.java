package blog.example.BlogApplication2.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Blogpost {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer postid;
    private String title;
    private String content;
    private Integer userid;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creationdate",nullable = false,updatable = false)
    private Date creationdate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lastmodifieddate")
    private Date lastmodifieddate;

    private String status;
    private String viewcount;
    private String image;
    @PrePersist
    protected void onCreate(){
        this.creationdate =new Date();
        this.lastmodifieddate=new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.lastmodifieddate=new Date();
    }
}
