package refrigerator.back.comment.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import refrigerator.back.comment.adapter.out.repository.query.CommentQueryRepository;
import refrigerator.back.comment.application.domain.Comment;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentQueryRepository {
    Optional<Comment> findByRecipeID(Long recipeID);
}
