package refrigerator.back.member.outbound.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.member.application.domain.QMember;

import static refrigerator.back.member.application.domain.QMember.*;

@Repository
@RequiredArgsConstructor
public class MemberLookUpQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 같은 이메일을 가진 회원 수 조회
     * @param email 사용자 이메일
     * @return 회원 수
     */
    public Integer selectCountOfMemberByEmail(String email){
        return jpaQueryFactory.select(member.count())
                .from(member)
                .where(member.email.eq(email))
                .fetchOne().intValue();
    }
}
