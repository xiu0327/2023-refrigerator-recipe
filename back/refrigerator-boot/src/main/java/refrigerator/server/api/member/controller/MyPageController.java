package refrigerator.server.api.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.server.api.authentication.GetMemberEmailUseCase;
import refrigerator.back.member.application.dto.MemberDto;
import refrigerator.back.member.application.port.in.GetMyProfileUseCase;
import refrigerator.back.myscore.application.dto.InMyScorePreviewsDto;
import refrigerator.back.myscore.application.port.in.FindMyScorePreviewsUseCase;
import refrigerator.server.api.member.dto.response.MyPageDto;
import refrigerator.server.api.member.dto.response.ProfileImageSelectListDto;


@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final GetMyProfileUseCase myProfileUseCase;
    private final FindMyScorePreviewsUseCase findMyScorePreviewsUseCase;
    private final GetMemberEmailUseCase getMemberEmailUseCase;

    @GetMapping("/api/my-page/member")
    public MyPageDto myPage(){
        String email = getMemberEmailUseCase.getMemberEmail();
        MemberDto memberDto = myProfileUseCase.getMyProfile(email);
        InMyScorePreviewsDto myScorePreviews = findMyScorePreviewsUseCase.findMyScorePreviews(email, 5);
        return null;
    }

    @GetMapping("/api/my-page/member/profile/images")
    public ProfileImageSelectListDto getProfileImages(){
        return new ProfileImageSelectListDto(myProfileUseCase.getProfileImages());
    }

}
