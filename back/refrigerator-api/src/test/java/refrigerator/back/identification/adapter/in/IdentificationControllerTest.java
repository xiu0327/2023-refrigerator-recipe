//package refrigerator.back.identification.adapter.in;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//import refrigerator.back.identification.adapter.dto.CheckNumberRequestDTO;
//import refrigerator.back.identification.application.port.in.SendNumberUseCase;
//import refrigerator.back.member.adapter.in.dto.request.MemberJoinRequestDTO;
//
//import javax.servlet.http.Cookie;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@AutoConfigureMockMvc
//@Transactional
//@SpringBootTest
//class IdentificationControllerTest {
//
//    @Autowired MockMvc mockMvc;
//    @Autowired SendNumberUseCase sendNumberUseCase;
//
//    @Test
//    @DisplayName("회원가입 본인인증 여부 확인 테스트")
//    void verityUser() throws Exception {
//        String email = "email123@gmail.com";
//        ObjectMapper objectMapper = new ObjectMapper();
//        // ------ 1. 본인인증 ------
//        String code = sendNumberUseCase.sendAuthenticationNumber(email, 8000L);
//        CheckNumberRequestDTO checkRequestDto = new CheckNumberRequestDTO(email, code);
//        mockMvc.perform(post("/api/identification/check")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(checkRequestDto))
//        ).andExpect(cookie().value("Verified-User", email)
//        ).andExpect(status().is2xxSuccessful()
//        ).andDo(print());
//        // ------ 2. 회원가입 ------
//        MemberJoinRequestDTO request = new MemberJoinRequestDTO(email, "password123!", "닉네임");
//        mockMvc.perform(post("/api/members/join")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(request))
//                .cookie(new Cookie("Verified-User", email))
//        ).andExpect(status().is2xxSuccessful()
//        ).andDo(print());
//    }
//
//}