//package refrigerator.server.api.comment.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.BDDMockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import refrigerator.server.api.authentication.GetMemberEmailUseCase;
//import refrigerator.back.comment.application.port.in.WriteCommentUseCase;
//import refrigerator.server.api.comment.dto.InCommentWriteRequestDto;
//import refrigerator.server.config.ExcludeSecurityConfiguration;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(controllers = CommentWriteController.class)
//@Import(ExcludeSecurityConfiguration.class)
//class CommentWriteControllerTest {
//
//    @Autowired MockMvc mvc;
//    @MockBean WriteCommentUseCase writeCommentUseCase;
//    @MockBean GetMemberEmailUseCase memberInformation;
//    ObjectMapper objectMapper = new ObjectMapper();
//
//    @Test
//    @DisplayName("댓글 작성 api 성공 테스트")
//    void writeSuccessTest() throws Exception {
//        // given
//        long recipeId = 1L;
//        String memberId = "email";
//        String content = "content";
//        InCommentWriteRequestDto requestDto = new InCommentWriteRequestDto(recipeId, content);
//        String json = objectMapper.writeValueAsString(requestDto);
//        BDDMockito.given(memberInformation.getMemberEmail())
//                        .willReturn(memberId);
//        // TODO : CurrentTime 필드 주입
//        BDDMockito.given(writeCommentUseCase.write(recipeId, memberId, content))
//                        .willReturn(1L);
//        mvc.perform(post("/api/comments/write")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(json)
//        ).andExpect(status().is2xxSuccessful()
//        ).andDo(print());
//    }
//}