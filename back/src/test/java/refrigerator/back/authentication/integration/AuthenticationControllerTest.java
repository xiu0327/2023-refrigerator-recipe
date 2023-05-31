package refrigerator.back.authentication.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import refrigerator.back.annotation.RedisFlushAll;

import refrigerator.back.authentication.adapter.in.dto.IssueTemporaryAccessTokenRequestDTO;
import refrigerator.back.authentication.adapter.in.dto.LoginRequestDTO;
import refrigerator.back.authentication.adapter.in.dto.TokenDTO;
import refrigerator.back.authentication.application.port.in.LoginUseCase;
import refrigerator.back.member.application.port.in.WithdrawMemberUseCase;

import javax.servlet.http.Cookie;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@RedisFlushAll(beanName = "memberCacheRedisTemplate")
class AuthenticationControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired WithdrawMemberUseCase withdrawMemberUseCase;
    @Autowired LoginUseCase loginUseCase;

    ObjectMapper objectMapper = new ObjectMapper();
    private final String email = "mstest102@gmail.com";
    private final String password = "password123!";

    @Test
    @DisplayName("토큰 발행 테스트")
    void createToken() throws Exception {
        LoginRequestDTO request = LoginRequestDTO.builder()
                .email(email)
                .password(password)
                .build();
        // when
        String requestJson = new ObjectMapper().writeValueAsString(request);
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJson)
        ).andExpect(status().is2xxSuccessful()
        ).andExpect(jsonPath("$.grantType").isString()
        ).andExpect(jsonPath("$.accessToken").isString()
        ).andExpect(cookie().exists("Refresh-Token")
        ).andExpect(cookie().httpOnly("Refresh-Token", true)
        ).andDo(print());
    }

    @Test
    @DisplayName("토큰 발행 후 레시피 목록 조회 성공")
    void afterLoginSuccess() throws Exception {
        // given
        TokenDTO token = loginUseCase.login(email, password);
        // when
        mockMvc.perform(get("/api/recipe?page=0")
                .header(HttpHeaders.AUTHORIZATION, getHeader(token))
        ).andExpect(status().is2xxSuccessful()
        ).andExpect(jsonPath("$.data").isArray()
        ).andDo(print());
    }

    private String getHeader(TokenDTO token) {
        return token.getGrantType() + " " + token.getAccessToken();
    }

    @Test
    @DisplayName("토큰 발행 후 레시피 목록 조회 실패")
    void afterLoginFail() throws Exception {
        /* 탈퇴한 회원이 레시피 목록을 조회하려 시도하는 경우 */
        // given
        TokenDTO token = loginUseCase.login(email, password);
        withdrawMemberUseCase.withdrawMember(email);
        // when
        mockMvc.perform(get("/api/recipe?page=0")
                .header(HttpHeaders.AUTHORIZATION, getHeader(token))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    @DisplayName("토큰 재발행 성공")
    void reissueToken() throws Exception {
        TokenDTO token = loginUseCase.login(email, password);
        mockMvc.perform(post("/api/auth/reissue")
                .cookie(new Cookie("Refresh-Token", token.getRefreshToken()))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    @DisplayName("토큰 재발행 -> 쿠키 값이 유효하지 않는 경우")
    void reissueTokenFail1() throws Exception {
        mockMvc.perform(post("/api/auth/reissue")
                .cookie(new Cookie("Refresh-Token", "logout"))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    @DisplayName("토큰 재발행 -> 로그아웃된 사용자일 경우")
    void reissueTokenFail2() throws Exception {
        mockMvc.perform(post("/api/auth/reissue")
                .cookie(new Cookie("Logout-Token", "true"))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    @DisplayName("정상적인 로그아웃")
    void logoutSuccess() throws Exception {
        // given
        TokenDTO token = loginUseCase.login(email, password);
        // when
        mockMvc.perform(get("/api/auth/logout")
                .header(HttpHeaders.AUTHORIZATION, getHeader(token))
        ).andExpect(status().is2xxSuccessful()
        ).andExpect(cookie().maxAge("Refresh-Token", 0)
        ).andExpect(header().string(HttpHeaders.AUTHORIZATION, "")
        ).andDo(print());
    }


    @Test
    @DisplayName("비밀번호를 찾기 위한 임시 토큰 발급 -> 실패")
    void issueTemporaryToken() throws Exception {
        /* 이메일 입력 -> 인증 토큰 발행, 인증 토큰은 10분간 유효 */
        // given
        IssueTemporaryAccessTokenRequestDTO request = IssueTemporaryAccessTokenRequestDTO.builder()
                .email(email).build();
        String requestJson = objectMapper.writeValueAsString(request);
        // when
        mockMvc.perform(post("/api/auth/issue/temporary-token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andExpect(status().is2xxSuccessful()
        ).andExpect(jsonPath("$.authToken").isString()
        ).andDo(print());
    }

    @Test
    @DisplayName("비밀번호를 찾기 위한 임시 토큰 발급 -> 실패")
    void issueTemporaryTokenFail() throws Exception {
        /* 빈 문자열 또는 null 이 입력값으로 들어올 때 -> 에러 발생 */
        // given
        IssueTemporaryAccessTokenRequestDTO request = IssueTemporaryAccessTokenRequestDTO.builder()
                .email("").build();
        String requestJson = objectMapper.writeValueAsString(request);
        // when
        mockMvc.perform(post("/api/auth/issue/temporary-token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }
}