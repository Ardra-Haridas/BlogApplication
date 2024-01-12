package blog.example.BlogApplication2.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)

private Integer commentid;
    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "postid")
    private Blogpost blogpost;

    private String content;
    @OneToMany(mappedBy = "parentComment",cascade=CascadeType.ALL,orphanRemoval = true)
    private List<Comment> replies;

    @ManyToOne
    @JoinColumn(name = "parentcommentid")
    private Comment parentComment;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creationdate",nullable = false,updatable = false)
    private Date creationdate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lastmodifieddate")
    private Date lastmodifieddate;


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
