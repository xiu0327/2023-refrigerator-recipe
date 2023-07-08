package refrigerator.back.member.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.common.RandomNumber;
import refrigerator.back.global.time.CurrentTime;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.domain.MemberProfileImage;
import refrigerator.back.member.application.port.in.JoinUseCase;
import refrigerator.back.member.application.port.out.CreateMemberPort;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemberJoinService implements JoinUseCase {

    private final CreateMemberPort createMemberPort;
    private final CurrentTime<LocalDateTime> currentTime;
    private final RandomNumber<Integer> randomNumber;
    
    @Override
    @Transactional
    public Long join(String email, String password, String nickname) {
        MemberProfileImage profileImage = MemberProfileImage.pickUp(randomNumber.getNumber());
        LocalDateTime joinDateTime = currentTime.now();
        Member member = Member.join(email, password, nickname, profileImage, joinDateTime);
        return createMemberPort.createMember(member);
    }

}
