package blog.example.BlogApplication2.auth;

import blog.example.BlogApplication2.Model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {

    private  String token;
    private User user;
}
