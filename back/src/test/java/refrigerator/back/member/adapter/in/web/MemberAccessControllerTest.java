package refrigerator.back.member.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import refrigerator.back.authentication.application.port.out.CreateTokenPort;
import refrigerator.back.authentication.application.port.out.EncryptPasswordPort;
import refrigerator.back.global.TestData;
import refrigerator.back.identification.adapter.dto.CheckNumberRequestDTO;
import refrigerator.back.identification.application.port.in.SendNumberUseCase;
import refrigerator.back.member.adapter.in.dto.request.MemberEmailParameterRequestDTO;
import refrigerator.back.member.adapter.in.dto.request.MemberFindPasswordRequestDTO;
import refrigerator.back.member.adapter.in.dto.request.MemberJoinRequestDTO;
import refrigerator.back.member.adapter.in.dto.request.MemberUpdatePasswordRequestDTO;
import refrigerator.back.member.application.domain.Member;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class MemberAccessControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    WebApplicationContext context;
    @Autowired TestData testData;
    @Autowired CreateTokenPort createTokenPort;
    @Autowired EncryptPasswordPort encryptPasswordPort;

    @Before
    public void setting(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    void 비밀번호_찾기() throws Exception {
        /* 이메일 입력 -> 인증 토큰 발행, 인증 토큰은 10분간 유효 */
        // given
        String email = "email123@gmail.com";
        String password = "password123!";
        testData.createMemberByEmailAndPassword(email, password);
        MemberFindPasswordRequestDTO request = MemberFindPasswordRequestDTO.builder()
                .email(email).build();
        String requestJson = new ObjectMapper().writeValueAsString(request);
        // when
        mockMvc.perform(post("/api/members/password/find")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andExpect(status().is2xxSuccessful()
        ).andExpect(jsonPath("$.authToken").isString()
        ).andDo(print());
    }

    @Test
    void 비밀번호_찾기_실패() throws Exception {
        /* 빈 문자열 또는 null 이 입력값으로 들어올 때 -> 에러 발생 */
        // given
        String email = "email123@gmail.com";
        String password = "password123!";
        testData.createMemberByEmailAndPassword(email, password);
        MemberFindPasswordRequestDTO request = MemberFindPasswordRequestDTO.builder()
                .email("").build();
        String requestJson = new ObjectMapper().writeValueAsString(request);
        // when
        mockMvc.perform(post("/api/members/password/find")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    void 비밀번호_수정() throws Exception {
        /* 비밀번호 찾기를 통해 발급한 토큰을 헤더에 포함 -> 토큰으로 회원을 조회하고 비밀번호 수정 */
        // given
        String email = "email123@gmail.com";
        String password = "password123!";
        testData.createMemberByEmailAndPassword(email, password);
        String authToken = createTokenPort.createTokenWithDuration(email, password, 1000 * 2);// 테스트를 위해 2초간 토큰 유지
        // when
        String newPassword = "password123445!";
        MemberUpdatePasswordRequestDTO request = new MemberUpdatePasswordRequestDTO(newPassword);
        String requestJson = new ObjectMapper().writeValueAsString(request);
        mockMvc.perform(put("/api/members/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken)
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
        Member member = testData.findMemberByEmail(email);
        Assertions.assertThat(encryptPasswordPort.match(newPassword, member.getPassword())).isTrue();
    }

    @Test
    void 비밀번호_수정_실패() throws Exception {
        /* 비밀번호 형식이 옳지 않은 경우 -> 에러 발생 */
        // given
        String email = "email123@gmail.com";
        String password = "password123!";
        testData.createMemberByEmailAndPassword(email, password);
        String authToken = createTokenPort.createTokenWithDuration(email, password, 1000 * 2);// 테스트를 위해 2초간 토큰 유지
        // when
        String newPassword = "!";
        MemberUpdatePasswordRequestDTO request = new MemberUpdatePasswordRequestDTO(newPassword);
        String requestJson = new ObjectMapper().writeValueAsString(request);
        mockMvc.perform(put("/api/members/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken)
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
        Member member = testData.findMemberByEmail(email);
        Assertions.assertThat(encryptPasswordPort.match(newPassword, member.getPassword())).isFalse();
    }

    @Test
    void 이메일_중복_체크() throws Exception {
        String email = "email123@gmail.com";
        MemberEmailParameterRequestDTO request = new MemberEmailParameterRequestDTO(email);
        String requestJson = new ObjectMapper().writeValueAsString(request);
        mockMvc.perform(post("/api/members/email/duplicate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

}