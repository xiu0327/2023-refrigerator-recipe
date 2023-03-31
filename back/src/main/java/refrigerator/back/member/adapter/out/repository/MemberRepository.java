package refrigerator.back.member.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import refrigerator.back.member.application.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}
