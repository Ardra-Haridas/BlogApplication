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
@Table(name = "History")
public class BlogpostHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private  Integer historyid;
@ManyToOne
@JoinColumn(name = "postid")
    private  Blogpost blogpost;

    private  String  content;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;


    @Temporal(TemporalType.TIMESTAMP)
    private Date  time;

}
