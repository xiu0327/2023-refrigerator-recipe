package refrigerator.back.global;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.adapter.out.persistence.entity.MemberEntity;
import refrigerator.back.member.application.domain.MemberStatus;
import refrigerator.back.member.exception.MemberExceptionType;

import javax.persistence.EntityManager;

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
                .memberStatus(MemberStatus.STEADY_STATUS.getStatusName()).build();
        em.persist(member);
        em.flush();
        em.clear();
    }

    @Transactional(readOnly = true)
    public MemberEntity findMemberByEmail(String email){
        return em.createQuery("select m from MemberEntity m where m.email= :email", MemberEntity.class)
                .setParameter("email", email)
                .getResultList().stream().findAny()
                .orElseThrow(() -> new BusinessException(MemberExceptionType.NOT_FOUND_MEMBER));
    }
}
