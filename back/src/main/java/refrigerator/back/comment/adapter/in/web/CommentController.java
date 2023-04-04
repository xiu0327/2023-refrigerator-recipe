package refrigerator.back.comment.adapter.in.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.comment.adapter.in.dto.request.EditCommentRequestDTO;
import refrigerator.back.comment.adapter.in.dto.request.WriteCommentRequestDTO;
import refrigerator.back.comment.adapter.in.dto.response.CommentBasicResponseDTO;
import refrigerator.back.comment.application.port.in.comment.DeleteCommentUseCase;
import refrigerator.back.comment.application.port.in.comment.EditCommentUseCase;
import refrigerator.back.comment.application.port.in.comment.WriteCommentUseCase;

import static refrigerator.back.global.common.MemberInformation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final WriteCommentUseCase writeCommentUseCase;
    private final EditCommentUseCase editCommentUseCase;
    private final DeleteCommentUseCase deleteCommentUseCase;

    @PostMapping("/api/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentBasicResponseDTO write(@RequestBody WriteCommentRequestDTO request){
        String memberId = getMemberEmail();
        Long commentId = writeCommentUseCase.write(
                request.getRecipeId(),
                memberId,
                request.getContent());
        return new CommentBasicResponseDTO(commentId);
    }

    @PutMapping("/api/comments")
    public CommentBasicResponseDTO edit(@RequestBody EditCommentRequestDTO request){
        Long commentId = editCommentUseCase.edit(
                getMemberEmail(),
                request.getCommentId(),
                request.getContent());
        return new CommentBasicResponseDTO(commentId);
    }

    @DeleteMapping("/api/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("commentId") Long commentId){
        deleteCommentUseCase.delete(
                getMemberEmail(),
                commentId);
    }
}
