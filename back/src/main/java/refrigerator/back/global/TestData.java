package refrigerator.back.global;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.domain.MemberProfileImage;
import refrigerator.back.member.application.domain.MemberStatus;
import refrigerator.back.member.exception.MemberExceptionType;
import refrigerator.back.myscore.application.domain.MyScore;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TestData {

    public final static String MEMBER_EMAIL = "email123@naver.com";
    public final static String MEMBER_PASSWORD = "password123!";
    public final static String MEMBER_NICKNAME = "닉네임뿅";
    private final EntityManager em;

    @Transactional
    public void createMember(){
        Member member = Member.builder()
                .email(MEMBER_EMAIL)
                .password(MEMBER_PASSWORD)
                .nickname(MEMBER_NICKNAME)
                .profile(MemberProfileImage.PROFILE_IMAGE_FIVE)
                .memberStatus(MemberStatus.STEADY_STATUS).build();
        em.persist(member);
        em.flush();
        em.clear();
    }

    @Transactional
    public String createMemberByEmail(String email){
        Member member = Member.builder()
                .email(email)
                .password(MEMBER_PASSWORD)
                .nickname(MEMBER_NICKNAME)
                .profile(MemberProfileImage.PROFILE_IMAGE_FIVE)
                .memberStatus(MemberStatus.STEADY_STATUS).build();
        em.persist(member);
        em.flush();
        em.clear();
        return member.getEmail();
    }

    @Transactional
    public Long createMyRecipeScore(String memberId, Long recipeId, Double score){
        MyScore myRecipeScore = MyScore.builder()
                .memberID(memberId)
                .recipeID(recipeId)
                .score(score).build();
        em.persist(myRecipeScore);
        em.flush();
        em.clear();
        return myRecipeScore.getScoreID();
    }

    @Transactional(readOnly = true)
    public Member findMemberByEmail(String email){
        return em.createQuery("select m from Member m where m.email= :email", Member.class)
                .setParameter("email", email)
                .getResultList().stream().findAny()
                .orElseThrow(() -> new BusinessException(MemberExceptionType.NOT_FOUND_MEMBER));
    }
}
