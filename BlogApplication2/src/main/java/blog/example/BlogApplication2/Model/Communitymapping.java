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
@Table(name = "communityusermapping")
public class Communitymapping {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer  mappingid;
    @ManyToOne
    @JoinColumn(name = "communityid")
    private Community community;
    @ManyToOne
    @JoinColumn(name="userid")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date joindate;

    @PrePersist
    protected void onCreate(){
        this.joindate =new Date();
    }

}
