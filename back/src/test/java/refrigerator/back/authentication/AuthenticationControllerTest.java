package refrigerator.back.authentication;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import refrigerator.back.annotation.RedisFlushAll;
import refrigerator.back.authentication.adapter.in.dto.LoginRequestDTO;
import refrigerator.back.authentication.adapter.in.dto.TokenDTO;
import refrigerator.back.authentication.application.port.in.LoginUseCase;
import refrigerator.back.global.config.BasicRedisConfig;
import refrigerator.back.member.adapter.cache.MemberRedisConfig;
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
    @Autowired WebApplicationContext context;
    @Autowired WithdrawMemberUseCase withdrawMemberUseCase;
    @Autowired LoginUseCase loginUseCase;

    private final String email = "nhtest@gmail.com";
    private final String password = "password123!";
    @Before
    public void setting(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

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
}