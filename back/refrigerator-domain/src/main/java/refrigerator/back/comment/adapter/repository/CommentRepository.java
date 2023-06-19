package refrigerator.back.comment.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.comment.adapter.repository.query.CommentHeartQueryRepository;
import refrigerator.back.comment.adapter.repository.query.CommentQueryRepository;
import refrigerator.back.comment.application.domain.Comment;


import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentQueryRepository, CommentHeartQueryRepository {
    @Query("select c from Comment c where c.deletedState=false and c.recipeID= :recipeID")
    Optional<Comment> findByRecipeID(@Param("recipeID") Long recipeID);

    @Query("select c from Comment c where c.deletedState=false and c.commentID= :id")
    Optional<Comment> findByCommentID(@Param("id") Long id);

    /** batch */

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("delete from Comment c where c.deletedState = true")
    void deleteComment();
}
