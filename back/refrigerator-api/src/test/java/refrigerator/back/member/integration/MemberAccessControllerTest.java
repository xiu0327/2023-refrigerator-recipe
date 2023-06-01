package refrigerator.back.member.integration;

import com.fasterxml.jackson.databind.ObjectMapper;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.annotation.RedisFlushAll;
import refrigerator.back.authentication.application.port.out.CreateTokenPort;
import refrigerator.back.member.adapter.in.dto.request.MemberEmailParameterRequestDTO;
import refrigerator.back.member.adapter.in.dto.request.MemberJoinRequestDTO;
import refrigerator.back.member.adapter.in.dto.request.MemberUpdatePasswordRequestDTO;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
@RedisFlushAll(beanName = "redisTemplate")
class MemberAccessControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired CreateTokenPort createTokenPort;
    ObjectMapper objectMapper = new ObjectMapper();

    String email = "nhtest@gmail.com";
    String password = "password123!";


    @Test
    void 회원_가입() throws Exception {
        MemberJoinRequestDTO dto = new MemberJoinRequestDTO(
                "email@gmail.com",
                "password123!",
                "닉네임");
        String json = objectMapper.writeValueAsString(dto);
        mockMvc.perform(post("/api/members/join")
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(new Cookie("Verified-User","email@gmail.com"))
                .content(json)
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    void 중복된_이메일() throws Exception {
        MemberEmailParameterRequestDTO dto = new MemberEmailParameterRequestDTO(email);
        String json = objectMapper.writeValueAsString(dto);
        mockMvc.perform(post("/api/members/email/duplicate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }


    @Test
    void 비밀번호_수정() throws Exception {
        /* 비밀번호 찾기를 통해 발급한 토큰을 헤더에 포함 -> 토큰으로 회원을 조회하고 비밀번호 수정 */
        // given
        String authToken = createTokenPort.createTokenWithDuration(email, password, 1000 * 2);// 테스트를 위해 2초간 토큰 유지
        // when
        String newPassword = "password123445!";
        MemberUpdatePasswordRequestDTO request = new MemberUpdatePasswordRequestDTO(newPassword);
        String requestJson = objectMapper.writeValueAsString(request);
        mockMvc.perform(put("/api/members/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken)
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    void 비밀번호_수정_실패() throws Exception {
        /* 비밀번호 형식이 옳지 않은 경우 -> 에러 발생 */
        // given
        String authToken = createTokenPort.createTokenWithDuration(email, password, 1000 * 2);// 테스트를 위해 2초간 토큰 유지
        // when
        String newPassword = "!";
        MemberUpdatePasswordRequestDTO request = new MemberUpdatePasswordRequestDTO(newPassword);
        String requestJson = objectMapper.writeValueAsString(request);
        mockMvc.perform(put("/api/members/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken)
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    void 이메일_중복_체크() throws Exception {
        String email = "email123@gmail.com";
        MemberEmailParameterRequestDTO request = new MemberEmailParameterRequestDTO(email);
        String requestJson = objectMapper.writeValueAsString(request);
        mockMvc.perform(post("/api/members/email/duplicate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

}