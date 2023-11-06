package blog.example.BlogApplication2.RepositoryTest;
import blog.example.BlogApplication2.Repository.LikeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LikeRepositoryTest {
    @Autowired
    private LikeRepository likeRepository;

    @Mock
    private Query query;

    @Mock
    private EntityManager entityManager;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExistByPostIdAndUserId() {
        when(entityManager.createNativeQuery("SELECT count(*) FROM Likes as l WHERE l.postid = ?1 AND l.userid = ?2")).thenReturn(query);
        when(query.setParameter(1, 1)).thenReturn(query);
        when(query.setParameter(2, 1)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(0);
        Integer result = likeRepository.existsByPostIdAndUserId(1, 1);
        assertEquals(0, result);
    }

    @Test
    void testExistByCommentIdAndUserID() {
        when(entityManager.createNativeQuery("SELECT count(*) FROM Likes as l WHERE l.commentid = ?1")).thenReturn(query);
        when(query.setParameter(1, 1)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(0);
        Integer result = likeRepository.existsByCommentIdAndUserId(1);
        assertEquals(0, result);
    }

}
