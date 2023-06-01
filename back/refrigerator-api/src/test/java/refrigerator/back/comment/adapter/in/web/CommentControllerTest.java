package refrigerator.back.comment.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.annotation.RedisFlushAll;
import refrigerator.back.authentication.adapter.in.dto.TokenDTO;
import refrigerator.back.authentication.application.port.in.LoginUseCase;
import refrigerator.back.authentication.application.port.out.EncryptPasswordPort;
import refrigerator.back.comment.adapter.in.dto.request.EditCommentRequestDTO;
import refrigerator.back.comment.adapter.in.dto.request.WriteCommentRequestDTO;
import refrigerator.back.comment.application.domain.CommentHeart;
import refrigerator.back.comment.application.port.in.comment.DeleteCommentUseCase;
import refrigerator.back.comment.application.port.in.comment.WriteCommentUseCase;
import refrigerator.back.comment.application.port.in.heart.AddCommentHeartUseCase;
import refrigerator.back.global.TestData;

import javax.persistence.EntityManager;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
@RedisFlushAll(beanName = "memberCacheRedisTemplate")
class CommentControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired EncryptPasswordPort passwordEncoder;
    @Autowired LoginUseCase loginUseCase;
    @Autowired TestData testData;
    @Autowired WriteCommentUseCase writeCommentUseCase;
    @Autowired DeleteCommentUseCase deleteCommentUseCase;
    @Autowired AddCommentHeartUseCase addCommentHeartUseCase;
    @Autowired EntityManager em;


    @Test
    void 댓글_작성() throws Exception {
        // given
        String rawPassword = "password123!";
        String email = testData.createMemberByEmailAndPassword("comment123@gmail.com", passwordEncoder.encrypt(rawPassword));
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

    @Test
    void 댓글_수정() throws Exception {
        // given
        String rawPassword = "password123!";
        String email = testData.createMemberByEmailAndPassword("comment123@gmail.com", passwordEncoder.encrypt(rawPassword));
        TokenDTO token = loginUseCase.login(email, rawPassword);
        Long commentId = writeCommentUseCase.write(1L, email, "맛있음");
        EditCommentRequestDTO request = new EditCommentRequestDTO(commentId, "개맛있음");
        String requestJson = new ObjectMapper().writeValueAsString(request);
        // when
        mockMvc.perform(put("/api/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .header(HttpHeaders.AUTHORIZATION, token.getGrantType() + " " + token.getAccessToken())
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    void 삭제된_댓글_수정() throws Exception {
        // given
        String rawPassword = "password123!";
        String email = testData.createMemberByEmailAndPassword("comment123@gmail.com", passwordEncoder.encrypt(rawPassword));
        TokenDTO token = loginUseCase.login(email, rawPassword);
        Long commentId = writeCommentUseCase.write(1L, email, "맛있음");
        deleteCommentUseCase.delete(email, commentId);
        EditCommentRequestDTO request = new EditCommentRequestDTO(commentId, "개맛있음");
        String requestJson = new ObjectMapper().writeValueAsString(request);
        // when
        mockMvc.perform(put("/api/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .header(HttpHeaders.AUTHORIZATION, token.getGrantType() + " " + token.getAccessToken())
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    void 댓글_삭제() throws Exception {
        // given
        String rawPassword = "password123!";
        String email = testData.createMemberByEmailAndPassword("comment123@gmail.com", passwordEncoder.encrypt(rawPassword));
        TokenDTO token = loginUseCase.login(email, rawPassword);
        Long commentId = writeCommentUseCase.write(1L, email, "맛있음");
        // when
        mockMvc.perform(delete("/api/comments/" + commentId)
                .header(HttpHeaders.AUTHORIZATION, token.getGrantType() + " " + token.getAccessToken())
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    void 댓글_생성_후_하트_누르기() throws Exception {
        // given
        String rawPassword = "password123!";
        String email = testData.createMemberByEmailAndPassword("comment123@gmail.com", passwordEncoder.encrypt(rawPassword));
        TokenDTO token = loginUseCase.login(email, rawPassword);
        Long commentId = writeCommentUseCase.write(1L, email, "맛있음");
        // when
        mockMvc.perform(post("/api/comments/"+ commentId +"/heart/add")
                .header(HttpHeaders.AUTHORIZATION, token.getGrantType() + " " + token.getAccessToken())
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
        CommentHeart findHeart = em.find(CommentHeart.class, commentId);
        Assertions.assertThat(findHeart.getCount()).isEqualTo(1);
    }

    @Test
    void 삭제된_댓글_하트_누르기() throws Exception {
        // given
        String rawPassword = "password123!";
        String email = testData.createMemberByEmailAndPassword("comment123@gmail.com", passwordEncoder.encrypt(rawPassword));
        TokenDTO token = loginUseCase.login(email, rawPassword);
        Long commentId = writeCommentUseCase.write(1L, email, "맛있음");
        deleteCommentUseCase.delete(email, commentId);
        // when
        mockMvc.perform(post("/api/comments/"+ commentId +"/heart/add")
                .header(HttpHeaders.AUTHORIZATION, token.getGrantType() + " " + token.getAccessToken())
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
        CommentHeart findHeart = em.find(CommentHeart.class, commentId);
        Assertions.assertThat(findHeart.getDeleteStatus()).isTrue();
    }

    @Test
    void 중복된_댓글_하트_누르기() throws Exception {
        // given
        String rawPassword = "password123!";
        String email = testData.createMemberByEmailAndPassword("comment123@gmail.com", passwordEncoder.encrypt(rawPassword));
        TokenDTO token = loginUseCase.login(email, rawPassword);
        Long commentId = writeCommentUseCase.write(1L, email, "맛있음");
        addCommentHeartUseCase.addHeart(email, commentId);
        // when
        mockMvc.perform(post("/api/comments/"+ commentId +"/heart/add")
                .header(HttpHeaders.AUTHORIZATION, token.getGrantType() + " " + token.getAccessToken())
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
        CommentHeart findHeart = em.find(CommentHeart.class, commentId);
        Assertions.assertThat(findHeart.getCount()).isEqualTo(1);
    }

    @Test
    void 중복된_댓글_하트_취소() throws Exception {
        // given
        String rawPassword = "password123!";
        String email = testData.createMemberByEmailAndPassword("comment123@gmail.com", passwordEncoder.encrypt(rawPassword));
        TokenDTO token = loginUseCase.login(email, rawPassword);
        Long commentId = writeCommentUseCase.write(1L, email, "맛있음");
        // when
        mockMvc.perform(post("/api/comments/"+ commentId +"/heart/reduce")
                .header(HttpHeaders.AUTHORIZATION, token.getGrantType() + " " + token.getAccessToken())
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
        CommentHeart findHeart = em.find(CommentHeart.class, commentId);
        Assertions.assertThat(findHeart.getCount()).isEqualTo(0);
    }

}