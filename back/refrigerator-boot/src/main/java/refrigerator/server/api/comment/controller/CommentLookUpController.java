package refrigerator.server.api.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.comment.application.dto.CommentListAndCountDTO;
import refrigerator.back.comment.application.dto.InCommentDTO;
import refrigerator.back.comment.application.port.in.comment.FindCommentListUseCase;
import refrigerator.back.comment.application.port.in.comment.FindCommentPreviewListUseCase;
import refrigerator.back.comment.application.port.in.comment.FindMyCommentsUseCase;
import refrigerator.back.authentication.application.port.in.GetMemberEmailUseCase;


@RestController
@RequiredArgsConstructor
public class CommentLookUpController {

    private final FindCommentPreviewListUseCase findCommentPreviewListUseCase;
    private final FindCommentListUseCase findCommentListUseCase;
    private final FindMyCommentsUseCase findMyCommentsUseCase;
    private final GetMemberEmailUseCase memberInformation;

    @GetMapping("/api/recipe/{recipeId}/comments/heart")
    public CommentListAndCountDTO<InCommentDTO> findCommentByHeart(
            @PathVariable("recipeId") Long recipeId,
            @RequestParam("page") int page,
            @RequestParam(value = "size", defaultValue = "11") int size){

        return CommentListAndCountDTO.<InCommentDTO>builder()
                .comments(findCommentListUseCase.findCommentsByHeart(recipeId, memberInformation.getMemberEmail(), page, size))
                .build();
    }

    @GetMapping("/api/recipe/{recipeId}/comments/date")
    public CommentListAndCountDTO<InCommentDTO> findCommentByDate(
            @PathVariable("recipeId") Long recipeId,
            @RequestParam("page") int page,
            @RequestParam(value = "size", defaultValue = "11") int size){

        return CommentListAndCountDTO.<InCommentDTO>builder()
                .comments(findCommentListUseCase.findCommentsByDate(recipeId, memberInformation.getMemberEmail(), page, size))
                .build();
    }

    @GetMapping("/api/recipe/{recipeId}/comments/preview")
    public CommentListAndCountDTO<InCommentDTO> findCommentPreview(
            @PathVariable("recipeId") Long recipeId,
            @RequestParam(value = "size", defaultValue = "3") int size){

        return findCommentPreviewListUseCase.findCommentPreviews(recipeId, memberInformation.getMemberEmail(), size);
    }

    @GetMapping("/api/recipe/{recipeId}/comments/my")
    public CommentListAndCountDTO<InCommentDTO> findMyComments(@PathVariable("recipeId") Long recipeId){

        return CommentListAndCountDTO.<InCommentDTO>builder()
                .comments(findMyCommentsUseCase.findMyComments(memberInformation.getMemberEmail(), recipeId))
                .build();
    }
}
