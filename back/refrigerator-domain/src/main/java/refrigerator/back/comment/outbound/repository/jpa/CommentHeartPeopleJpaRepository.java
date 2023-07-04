package refrigerator.back.comment.outbound.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import refrigerator.back.comment.application.domain.OldCommentHeartPeople;

public interface CommentHeartPeopleJpaRepository extends JpaRepository<OldCommentHeartPeople, Long> {
}
