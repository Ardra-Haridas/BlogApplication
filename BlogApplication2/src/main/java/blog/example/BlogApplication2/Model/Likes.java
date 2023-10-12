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
@Table(name = "Likes")
public class Likes {
@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer likeid;
@ManyToOne
@JoinColumn(name = "userid")
    private  User user;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "postid")
    private Blogpost blogpost;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "commentid")
    private  Comment comment;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "liketime",nullable = false)
    private Date liketime;
}
