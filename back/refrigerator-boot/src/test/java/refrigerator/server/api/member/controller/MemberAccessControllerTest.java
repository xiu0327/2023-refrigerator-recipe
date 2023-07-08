package refrigerator.server.api.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.application.port.in.EmailDuplicationCheckUseCase;
import refrigerator.back.member.exception.MemberExceptionType;
import refrigerator.server.api.member.cookie.MemberEmailCheckCookieAdapter;
import refrigerator.server.api.member.dto.request.MemberEmailCheckRequestDto;
import refrigerator.server.config.ExcludeSecurityConfiguration;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberAccessController.class)
@Import(ExcludeSecurityConfiguration.class)
class MemberAccessControllerTest {

    @Autowired MockMvc mvc;
    @MockBean
    EmailDuplicationCheckUseCase duplicateCheckEmailUseCase;
    @MockBean MemberEmailCheckCookieAdapter memberEmailCheckCookieAdapter;
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("이메일 중복 확인")
    void duplicateCheckEmail() throws Exception {
        // given
        String email = "email123@gmail.com";
        MemberEmailCheckRequestDto requestDto = new MemberEmailCheckRequestDto(email);
        String json = objectMapper.writeValueAsString(requestDto);
        String cookieName = "cookieName";
        String cookieValue = "cookieValue";
        BDDMockito.given(memberEmailCheckCookieAdapter.create())
                        .willReturn(new Cookie(cookieName, cookieValue));
        // when & then
        mvc.perform(post("/api/members/email/duplicate")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is2xxSuccessful()
        ).andExpect(cookie().value(cookieName, cookieValue)
        ).andDo(print());
    }

    @Test
    @DisplayName("이메일 중복 확인 실패 -> 중복된 이메일")
    void duplicateCheckEmailFailTest1() throws Exception {
        // given
        String email = "email123@gmail.com";
        MemberEmailCheckRequestDto requestDto = new MemberEmailCheckRequestDto(email);
        String json = objectMapper.writeValueAsString(requestDto);
        String cookieName = "cookieName";
        String cookieValue = "cookieValue";
        BDDMockito.given(memberEmailCheckCookieAdapter.create())
                .willReturn(new Cookie(cookieName, cookieValue));
        BDDMockito.doThrow(new BusinessException(MemberExceptionType.DUPLICATE_EMAIL))
                        .when(duplicateCheckEmailUseCase).duplicateCheck(email);
        // when & then
        mvc.perform(post("/api/members/email/duplicate")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is4xxClientError()
        ).andExpect(cookie().doesNotExist(cookieName)
        ).andDo(print());
    }

    @Test
    @DisplayName("이메일 중복 확인 실패 -> 올바르지 않은 이메일 형식")
    void duplicateCheckEmailFailTest2() throws Exception {
        // given
        String wrongEmail = "wrongEmail";
        MemberEmailCheckRequestDto requestDto = new MemberEmailCheckRequestDto(wrongEmail);
        String json = objectMapper.writeValueAsString(requestDto);
        String cookieName = "cookieName";
        // when & then
        mvc.perform(post("/api/members/email/duplicate")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is4xxClientError()
        ).andExpect(cookie().doesNotExist(cookieName)
        ).andExpect(jsonPath("$.code").isString()
        ).andExpect(jsonPath("$.message").isString()
        ).andDo(print());
    }
}