package refrigerator.back.member.application.port.in;


import refrigerator.back.member.adapter.in.dto.response.MemberProfileDTO;

import java.util.List;

public interface GetProfileListUseCase {
    List<MemberProfileDTO> getProfileList();
}
