package refrigerator.back.member.application.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import refrigerator.back.member.adapter.in.dto.response.MemberProfileDTO;
import refrigerator.back.member.application.domain.MemberProfileImage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberProfileImageServiceTest {

    @Autowired MemberProfileImageService memberProfileImageService;

    @Test
    void 이미지_url_생성() {
        String imageName = MemberProfileImage.PROFILE_IMAGE_ONE.getName();
        String imageUrl = memberProfileImageService.createURL(imageName);
        assertNotNull(imageUrl);
    }

    @Test
    void 이미지_목록_조회() {
        List<MemberProfileDTO> profileList = memberProfileImageService.getProfileList();
        Assertions.assertThat(profileList.size()).isEqualTo(MemberProfileImage.values().length);
    }
}