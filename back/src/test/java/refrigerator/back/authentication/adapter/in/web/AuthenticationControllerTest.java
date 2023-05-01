package refrigerator.back.authentication.adapter.in.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import refrigerator.back.authentication.adapter.in.dto.LoginRequestDTO;
import refrigerator.back.authentication.adapter.in.dto.ReissueTokenRequestDTO;
import refrigerator.back.authentication.adapter.in.dto.TokenDTO;
import refrigerator.back.authentication.application.port.in.LoginUseCase;
import refrigerator.back.global.TestData;
import refrigerator.back.member.application.port.in.WithdrawMemberUseCase;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthenticationControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired WebApplicationContext context;
    @Autowired TestData testData;
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired WithdrawMemberUseCase withdrawMemberUseCase;
    @Autowired LoginUseCase loginUseCase;
    @Autowired EntityManager em;

    @Before
    public void setting(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    void 토큰_발행() throws Exception {
        // given
        String password = "password123!";
        String email = testData.createMemberByEmailAndPassword("email123@gmail.com", passwordEncoder.encode(password));
        LoginRequestDTO request = LoginRequestDTO.builder()
                .email(email)
                .password(password)
                .build();
        // when
        request.check();
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
    void 토큰_발행_후_레시피_목록_조회() throws Exception {
        // given
        String password = "password123!";
        String email = testData.createMemberByEmailAndPassword("email123@gmail.com", passwordEncoder.encode(password));
        TokenDTO token = loginUseCase.login(email, password);
        // when
        mockMvc.perform(get("/api/recipe?page=0")
                .header(HttpHeaders.AUTHORIZATION, token.getGrantType() + " " + token.getAccessToken())
        ).andExpect(status().is2xxSuccessful()
        ).andExpect(jsonPath("$.data").isArray()
        ).andDo(print());
    }

    @Test
    void 토큰_발행_후_레시피_목록_조회_실패() throws Exception {
        /* 탈퇴한 회원이 레시피 목록을 조회하려 시도하는 경우 */
        // given
        String password = "password123!";
        String email = testData.createMemberByEmailAndPassword("email123@gmail.com", passwordEncoder.encode(password));
        TokenDTO token = loginUseCase.login(email, password);
        withdrawMemberUseCase.withdrawMember(email, password);
        em.flush();
        em.clear();
        // when
        mockMvc.perform(get("/api/recipe?page=0")
                .header(HttpHeaders.AUTHORIZATION, token.getGrantType() + " " + token.getAccessToken())
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    void 토큰_재발행() throws Exception {
        // given
        String password = "password123!";
        String email = testData.createMemberByEmailAndPassword("email123@gmail.com", passwordEncoder.encode(password));
        TokenDTO token = loginUseCase.login(email, password);
        // when
        ReissueTokenRequestDTO request = new ReissueTokenRequestDTO(token.getRefreshToken());
        String requestJson = new ObjectMapper().writeValueAsString(request);
        mockMvc.perform(post("/api/auth/reissue")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }
}