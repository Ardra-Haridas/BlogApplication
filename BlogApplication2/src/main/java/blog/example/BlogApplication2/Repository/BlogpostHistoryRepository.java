package blog.example.BlogApplication2.Repository;

import blog.example.BlogApplication2.Model.BlogpostHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogpostHistoryRepository extends JpaRepository<BlogpostHistory,Integer> {
}
