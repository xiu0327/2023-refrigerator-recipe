package refrigerator.server.api.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.authentication.application.port.in.GetMemberEmailUseCase;
import refrigerator.server.api.comment.dto.InCommentIdResponseDto;
import refrigerator.back.comment.application.port.in.comment.WriteCommentUseCase;
import refrigerator.server.api.comment.dto.InCommentWriteRequestDto;
import refrigerator.server.api.global.exception.ValidationExceptionHandler;

import java.time.LocalDateTime;

import static refrigerator.back.comment.exception.CommentExceptionType.NOT_VALID_REQUEST_BODY;


@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentWriteController {

    private final WriteCommentUseCase writeCommentUseCase;
    private final GetMemberEmailUseCase memberInformation;

    @PostMapping("/api/comments/write")
    @ResponseStatus(HttpStatus.CREATED)
    public InCommentIdResponseDto write(@RequestBody InCommentWriteRequestDto request, BindingResult bindingResult){
        ValidationExceptionHandler.check(bindingResult, NOT_VALID_REQUEST_BODY);
        String memberId = memberInformation.getMemberEmail();
        Long commentId = writeCommentUseCase.write(
                request.getRecipeId(),
                memberId,
                request.getContent(),
                LocalDateTime.now());
        return new InCommentIdResponseDto(commentId);
    }

}
