package refrigerator.server.api.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import refrigerator.server.api.authentication.GetMemberEmailUseCase;
import refrigerator.back.comment.application.port.in.WriteCommentUseCase;
import refrigerator.server.api.comment.dto.InCommentWriteRequestDto;
import refrigerator.server.api.global.exception.ValidationExceptionHandler;


import static refrigerator.back.comment.exception.CommentExceptionType.NOT_VALID_REQUEST_BODY;


@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentWriteController {

    private final WriteCommentUseCase writeCommentUseCase;
    private final GetMemberEmailUseCase memberInformation;

    @PostMapping("/api/comments/write")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void write(@RequestBody InCommentWriteRequestDto request, BindingResult bindingResult){
        ValidationExceptionHandler.check(bindingResult, NOT_VALID_REQUEST_BODY);
        String memberId = memberInformation.getMemberEmail();
        writeCommentUseCase.write(
                request.getRecipeId(),
                memberId,
                request.getContent());
    }

}
