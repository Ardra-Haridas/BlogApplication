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
@Table(name = "community")
public class Community {
@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer communityid;
    private String communityname;
    private Integer membercount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;


    @Temporal(TemporalType.TIMESTAMP)
    private Date modified_at;
    @PrePersist
    protected void onCreate(){
        this.created_at =new Date();
        this.modified_at=new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.modified_at=new Date();
    }


}
