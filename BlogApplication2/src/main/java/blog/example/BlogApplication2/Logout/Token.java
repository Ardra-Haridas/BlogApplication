package blog.example.BlogApplication2.Logout;

import blog.example.BlogApplication2.Model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer tokenid;

    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    private boolean expired;

    private boolean revoked;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

}
