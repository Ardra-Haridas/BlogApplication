package blog.example.BlogApplication2.auth;

import blog.example.BlogApplication2.Logout.Token;
import blog.example.BlogApplication2.Logout.TokenRepository;
import blog.example.BlogApplication2.Logout.TokenType;
import blog.example.BlogApplication2.config.JwtService;
import blog.example.BlogApplication2.Model.User;
import blog.example.BlogApplication2.Repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class AuthenticationService {
    @Autowired
    private final UserRepository repository;
    @Autowired
    private final TokenRepository tokenRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final AuthenticationManager authenticationManager;

    public Map<String,Object> register(User request) {
        Map<String,Object> res = new HashMap<>();
        if(repository.duplicateExist(request.getUsername(),request.getEmail()).isEmpty()) {
            User user = new User();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            var savedUser = repository.save(user);
            var jwtToken = jwtService.generateToken(user);
            saveUserToken(savedUser, jwtToken);
            AuthenticationResponse response = new AuthenticationResponse();
            response.setToken(jwtToken);
            response.setUser(savedUser);
            res.put("response",jwtToken);
            res.put("status",true);
            return res;
        }
        else{
            res.put("response","Registration Unsuccessfull!");
            res.put("status",false);
            return res;
        }
//        return AuthenticationResponse.builder()
//                .token(jwtToken)
//                .build();
    }


    public Map<String,Object> authenticate(AuthenticationRequest request) {
        Map<String,Object> res = new HashMap<>();
        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            var user = repository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new BadCredentialsException("User not Found"));
            var jwtToken = jwtService.generateToken(user);
            revokedAllUserTokens(user);
            saveUserToken(user,jwtToken);
            res.put("response",jwtToken);
            res.put("status",true);
            return res;
        } catch (AuthenticationException ex) {
            res.put("response","Invalid email or Password!");
            res.put("status",false);
            return res;
        }
    }
    private  void revokedAllUserTokens(User user){
        var validUserTokens =tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
    private void saveUserToken(User user, String jwtToken) {
        var token= Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }
}
