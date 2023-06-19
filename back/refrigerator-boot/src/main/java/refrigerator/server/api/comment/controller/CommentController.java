package refrigerator.server.api.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.comment.exception.CommentExceptionType;
import refrigerator.server.api.comment.dto.EditCommentRequestDTO;
import refrigerator.server.api.comment.dto.WriteCommentRequestDTO;
import refrigerator.back.comment.application.dto.CommentBasicDTO;
import refrigerator.back.comment.application.port.in.comment.DeleteCommentUseCase;
import refrigerator.back.comment.application.port.in.comment.EditCommentUseCase;
import refrigerator.back.comment.application.port.in.comment.WriteCommentUseCase;
import refrigerator.back.authentication.application.port.in.GetMemberEmailUseCase;
import refrigerator.server.api.global.exception.ValidationExceptionHandler;

import static refrigerator.back.comment.exception.CommentExceptionType.*;


@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final WriteCommentUseCase writeCommentUseCase;
    private final EditCommentUseCase editCommentUseCase;
    private final DeleteCommentUseCase deleteCommentUseCase;
    private final GetMemberEmailUseCase memberInformation;

    @PostMapping("/api/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentBasicDTO write(@RequestBody WriteCommentRequestDTO request, BindingResult bindingResult){
        ValidationExceptionHandler.check(bindingResult, NOT_VALID_REQUEST_BODY);

        return new CommentBasicDTO(writeCommentUseCase
                .write(request.getRecipeId(), memberInformation.getMemberEmail(),
                request.getContent()));
    }

    @PutMapping("/api/comments")
    public CommentBasicDTO edit(@RequestBody EditCommentRequestDTO request, BindingResult bindingResult){
        ValidationExceptionHandler.check(bindingResult, NOT_VALID_REQUEST_BODY);

        return new CommentBasicDTO(editCommentUseCase
                .edit(memberInformation.getMemberEmail(),
                request.getCommentID(),
                request.getContent()));
    }

    @DeleteMapping("/api/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("commentId") Long commentId){
        deleteCommentUseCase.delete(
                memberInformation.getMemberEmail(),
                commentId);
    }
}
