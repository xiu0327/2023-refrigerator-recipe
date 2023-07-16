package refrigerator.server.api.comment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.server.api.comment.dto.InCommentWriteRequestDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CommentControllerTest {

    @Autowired MockMvc mvc;
    ObjectMapper objectMapper = new ObjectMapper();


    @Test
    @DisplayName("댓글 작성 테스트")
    @WithUserDetails("jktest101@gmail.com")
    void write() throws Exception {
        // given
        long recipeId = 1L;
        String memberId = "jktest101@gmail.com";
        String content = "content";
        InCommentWriteRequestDto requestDto = new InCommentWriteRequestDto(recipeId, content);
        String json = objectMapper.writeValueAsString(requestDto);
        mvc.perform(post("/api/comments/write")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }
}