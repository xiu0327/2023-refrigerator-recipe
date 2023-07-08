//package refrigerator.back.member.application.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import refrigerator.back.global.exception.BusinessException;
//import refrigerator.back.member.application.domain.Member;
//import refrigerator.back.member.application.port.out.FindMemberPort;
//import refrigerator.back.member.exception.MemberExceptionType;
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class MemberValidationService {
//
//    private final FindMemberPort findMemberPort;
//
//    public void validateByEmail(String email){
//        Optional<Member> result = Optional.ofNullable(findMemberPort.findByEmail(email));
//        if (result.isEmpty()){
//            throw new BusinessException(MemberExceptionType.DUPLICATE_EMAIL);
//        }
//    }
//}
