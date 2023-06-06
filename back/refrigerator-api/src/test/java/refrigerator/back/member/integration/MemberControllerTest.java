package refrigerator.back.member.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.annotation.RedisFlushAll;
import refrigerator.back.authentication.application.port.out.CreateTokenPort;
import refrigerator.back.global.common.CustomCookie;
import refrigerator.back.member.adapter.in.dto.request.MemberNicknameUpdateRequestDTO;
import refrigerator.back.member.adapter.in.dto.request.MemberProfileUpdateRequestDTO;
import refrigerator.back.member.adapter.in.dto.request.MemberWithdrawRequestDTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest
@Transactional
@WithUserDetails(value = "nhtest@gmail.com")
class MemberControllerTest {

    @Autowired MockMvc mockMvc;

    @Test
    void 닉네임_수정() throws Exception {
        String nickname = "수정닉네임";
        MemberNicknameUpdateRequestDTO request = new MemberNicknameUpdateRequestDTO(nickname);
        String requestJson = new ObjectMapper().writeValueAsString(request);
        mockMvc.perform(put("/api/members/nickname")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    void 닉네임_수정_실패() throws Exception {
        /* 형식에 맞지 않는 닉네임으로 수정하려 할 때 */
        String nickname = "!#$@#%!@#%";
        MemberNicknameUpdateRequestDTO request = new MemberNicknameUpdateRequestDTO(nickname);
        String requestJson = new ObjectMapper().writeValueAsString(request);
        mockMvc.perform(put("/api/members/nickname")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    void 프로필_수정() throws Exception {
        String imageName = "IMG_9709.JPG";
        MemberProfileUpdateRequestDTO request = new MemberProfileUpdateRequestDTO(imageName);
        String requestJson = new ObjectMapper().writeValueAsString(request);
        mockMvc.perform(put("/api/members/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    void 프로필_수정_실패() throws Exception {
        /* 유효하지 않은 이미지 이름이 파라미터로 들어왔을 때 */
        String imageName = "imageimage.JPG";
        MemberProfileUpdateRequestDTO request = new MemberProfileUpdateRequestDTO(imageName);
        String requestJson = new ObjectMapper().writeValueAsString(request);
        mockMvc.perform(put("/api/members/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }


    @Test
    void 회원_정보_조회() throws Exception {
        mockMvc.perform(get("/api/members")
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    void 프로필_목록_조회() throws Exception {
        mockMvc.perform(get("/api/members/profile/list")
        ).andExpect(status().is2xxSuccessful()
        ).andExpect(jsonPath("$.data").isNotEmpty()
        ).andDo(print());
    }

}