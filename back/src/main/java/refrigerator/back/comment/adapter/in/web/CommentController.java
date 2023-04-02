package refrigerator.back.comment.adapter.in.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.comment.adapter.in.dto.request.WriteCommentRequestDTO;
import refrigerator.back.comment.adapter.in.dto.response.CommentBasicResponseDTO;
import refrigerator.back.comment.application.port.in.*;
import refrigerator.back.global.common.MemberInformation;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final WriteCommentUseCase writeCommentUseCase;
    private final EditCommentUseCase editCommentUseCase;
    private final DeleteCommentUseCase deleteCommentUseCase;
    private final FindCommentPreviewListUseCase findCommentPreviewListUseCase;
    private final FindCommentListUseCase findCommentListUseCase;

    @PostMapping("/api/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentBasicResponseDTO write(@RequestBody WriteCommentRequestDTO request){
        String memberId = MemberInformation.getMemberEmail();
        Long commentId = writeCommentUseCase.write(
                request.getRecipeId(),
                memberId,
                request.getContent());
        return new CommentBasicResponseDTO(commentId);
    }
}
