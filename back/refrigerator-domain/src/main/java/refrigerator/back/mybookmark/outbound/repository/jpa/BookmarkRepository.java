package refrigerator.back.mybookmark.outbound.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.mybookmark.application.domain.MyBookmark;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<MyBookmark, Long> {
}
