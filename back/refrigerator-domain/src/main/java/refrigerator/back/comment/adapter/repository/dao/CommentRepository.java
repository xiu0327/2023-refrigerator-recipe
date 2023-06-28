package refrigerator.back.comment.adapter.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.comment.application.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{
}
