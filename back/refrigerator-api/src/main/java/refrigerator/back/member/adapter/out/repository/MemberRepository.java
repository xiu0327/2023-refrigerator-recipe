package refrigerator.back.member.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.domain.MemberProfileImage;


import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    @Modifying
    @Query("update Member m set m.nickname= :nickname where m.email= :email")
    void updateNickname(@Param("email") String memberId, @Param("nickname") String nickname);
    @Modifying
    @Query("update Member m set m.profile= :profile where m.email= :email")
    void updateProfile(@Param("email") String memberId, @Param("profile") MemberProfileImage profile);
    @Modifying
    @Query("update Member m set m.profile= :profile, m.nickname= :nickname where m.email= :email")
    void initNicknameAndProfile(@Param("email") String memberId,
                                @Param("nickname") String nickname,
                                @Param("profile") MemberProfileImage profile);
}
