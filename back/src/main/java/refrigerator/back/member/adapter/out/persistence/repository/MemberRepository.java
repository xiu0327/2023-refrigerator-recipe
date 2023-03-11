package refrigerator.back.member.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import refrigerator.back.member.adapter.out.persistence.entity.MemberEntity;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByEmail(String email);
}
