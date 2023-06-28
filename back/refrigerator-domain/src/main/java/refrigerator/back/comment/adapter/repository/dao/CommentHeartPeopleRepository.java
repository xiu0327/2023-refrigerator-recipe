package refrigerator.back.comment.adapter.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import refrigerator.back.comment.application.domain.CommentHeartPeople;

public interface CommentHeartPeopleRepository extends JpaRepository<CommentHeartPeople, Long> {
}
