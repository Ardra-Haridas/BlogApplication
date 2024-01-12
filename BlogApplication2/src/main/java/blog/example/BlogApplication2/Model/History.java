package blog.example.BlogApplication2.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "history")
public class History {
@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
private  Integer historyid;
private String oldcontent;
private String oldtitle;
private String oldimage;
private String oldcomment;
@Enumerated(EnumType.STRING)
private ChangeType changetype;

@ManyToOne
@JoinColumn(name = "post_id")
private  Blogpost blogpost;
@ManyToOne
@JoinColumn(name="commentid")
private  Comment comment;

@Temporal(TemporalType.TIMESTAMP)
private Date time;



}
