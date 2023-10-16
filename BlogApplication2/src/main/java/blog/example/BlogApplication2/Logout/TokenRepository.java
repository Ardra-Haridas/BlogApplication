package blog.example.BlogApplication2.Logout;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Integer> {
    @Query(value = """
            select t.* from token t inner join user u on t.userid=u.id where u.id =:userid and (t.expired=false or t.revoked=false)
            """,nativeQuery = true)
    List<Token>findAllValidTokenByUser(Integer userid);

    Optional<Token>findByToken(String token);

}
