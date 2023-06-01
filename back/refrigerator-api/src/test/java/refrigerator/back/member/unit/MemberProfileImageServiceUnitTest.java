package refrigerator.back.member.unit;

import org.junit.jupiter.api.Test;
import refrigerator.back.member.adapter.in.dto.response.MemberProfileDTO;
import refrigerator.back.member.application.domain.MemberProfileImage;
import refrigerator.back.member.application.service.MemberProfileImageService;


import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemberProfileImageServiceUnitTest {

    MemberProfileImageService service = new MemberProfileImageService("route", "path");

    @Test
    void 이미지_url_생성() {
        String imageName = MemberProfileImage.PROFILE_IMAGE_ONE.getName();
        String imageUrl = service.createURL(imageName);
        assertThat(imageUrl).isNotNull();
        assertThat(imageUrl).isEqualTo("route" + "path" + imageName);
    }

    @Test
    void 프로필_이미지_목록_조회(){
        List<MemberProfileDTO> result = service.getProfileList();
        result.forEach(image -> {
            assertThat(image).isNotNull();
            assertThat(image.getImageUrl().startsWith("route" + "path")).isTrue();
        });
    }
}