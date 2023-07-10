package refrigerator.back.myscore.outbound.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import refrigerator.back.myscore.application.domain.MyScore;

public interface MyScoreJpaRepository extends JpaRepository<MyScore, Long> {
}
