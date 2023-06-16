package refrigerator.back.member.application.port.in;


import refrigerator.back.member.application.dto.MemberProfileDto;

import java.util.List;

public interface GetProfileListUseCase {
    List<MemberProfileDto> getProfileList();
}
