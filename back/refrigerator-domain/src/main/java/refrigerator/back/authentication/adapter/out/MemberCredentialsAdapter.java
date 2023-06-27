package refrigerator.back.authentication.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.authentication.adapter.out.repository.UserSelectQueryRepository;
import refrigerator.back.authentication.application.dto.UserDto;
import refrigerator.back.authentication.application.port.out.GetMemberCredentialsPort;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.exception.MemberExceptionType;

@Repository
@RequiredArgsConstructor
public class MemberCredentialsAdapter implements GetMemberCredentialsPort {

    private final UserSelectQueryRepository repository;

    @Override
    @Transactional(readOnly = true)
    public UserDto getUser(String username) {
        return repository.selectUserByName(username)
                .orElseThrow(() -> new BusinessException(MemberExceptionType.NOT_FOUND_MEMBER))
                .mapping();
    }
}
