package refrigerator.back.global;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.adapter.out.entity.MemberEntity;
import refrigerator.back.member.application.domain.MemberDomain;
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
        MemberEntity member = MemberEntity.builder()
                .email(MEMBER_EMAIL)
                .password(MEMBER_PASSWORD)
                .nickname(MEMBER_NICKNAME)
                .profile(MemberProfileImage.PROFILE_IMAGE_FIVE.getName())
                .memberStatus(MemberStatus.STEADY_STATUS.getStatusCode()).build();
        em.persist(member);
        em.flush();
        em.clear();
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
    public MemberEntity findMemberByEmail(String email){
        return em.createQuery("select m from MemberEntity m where m.email= :email", MemberEntity.class)
                .setParameter("email", email)
                .getResultList().stream().findAny()
                .orElseThrow(() -> new BusinessException(MemberExceptionType.NOT_FOUND_MEMBER));
    }

    public MemberDomain createMemberDomain(){
        return MemberDomain.builder()
                .memberID(1L)
                .email("email123@naver.com")
                .password("password123!")
                .nickname("닉네임")
                .joinDate(LocalDateTime.now())
                .memberStatus(MemberStatus.STEADY_STATUS)
                .profile(MemberProfileImage.PROFILE_IMAGE_FIVE)
                .build();
    }

    public MemberEntity createMemberEntity(){
        return MemberEntity.builder()
                .id(1L)
                .email("email123@naver.com")
                .password("password123!")
                .nickname("닉네임")
                .memberStatus(MemberStatus.STEADY_STATUS.getStatusCode())
                .profile(MemberProfileImage.PROFILE_IMAGE_FIVE.getName())
                .build();
    }
}
