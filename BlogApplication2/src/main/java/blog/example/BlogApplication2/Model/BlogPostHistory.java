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
public class BlogPostHistory {
@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
private  Integer historyid;

private  String newcontent;

private String newimage;

private String newtitle;

@ManyToOne
@JoinColumn(name = "post_id")
private  Blogpost blogpost;

@Temporal(TemporalType.TIMESTAMP)
private Date time;



}
