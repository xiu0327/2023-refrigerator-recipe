package refrigerator.back.member.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import refrigerator.back.member.adapter.in.dto.response.MemberProfileDTO;
import refrigerator.back.member.application.domain.MemberProfileImage;
import refrigerator.back.member.application.port.in.GetProfileListUseCase;
import refrigerator.back.member.application.port.in.MakeProfileUrlUseCase;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberProfileImageService implements MakeProfileUrlUseCase, GetProfileListUseCase {

    @Value("${s3.image.route}")
    private final String route;
    @Value("${s3.image.profile.path}")
    private final String path;

    @Override
    public String createURL(String imageName) {
        return route + path + imageName;
    }

    @Override
    public List<MemberProfileDTO> getProfileList() {
        return Arrays.stream(MemberProfileImage.values())
                .map(image -> new MemberProfileDTO(image.getName(), createURL(image.getName())))
                .collect(Collectors.toList());
    }
}
