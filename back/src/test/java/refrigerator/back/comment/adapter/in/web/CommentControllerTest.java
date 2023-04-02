package refrigerator.back.comment.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
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
import refrigerator.back.authentication.adapter.in.dto.TokenDTO;
import refrigerator.back.authentication.application.port.in.LoginUseCase;
import refrigerator.back.authentication.application.port.out.EncryptPasswordPort;
import refrigerator.back.comment.adapter.in.dto.request.WriteCommentRequestDTO;
import refrigerator.back.global.TestData;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class CommentControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired WebApplicationContext context;
    @Autowired EncryptPasswordPort passwordEncoder;
    @Autowired LoginUseCase loginUseCase;
    @Autowired TestData testData;

    @Before
    public void setting(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    void 댓글_작성() throws Exception {
        // given
        String rawPassword = "password123!";
        String email = testData.createMemberByEmailAndPassword("email123@gmail.com", passwordEncoder.encrypt(rawPassword));
        TokenDTO token = loginUseCase.login(email, rawPassword);
        WriteCommentRequestDTO request = WriteCommentRequestDTO.builder()
                .recipeId(1L)
                .content("맛있음")
                .build();
        String requestJson = new ObjectMapper().writeValueAsString(request);
        // when
        mockMvc.perform(post("/api/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .header(HttpHeaders.AUTHORIZATION, token.getGrantType() + " " + token.getAccessToken())
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }
}