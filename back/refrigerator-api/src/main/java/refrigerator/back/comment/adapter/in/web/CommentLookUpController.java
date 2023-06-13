package refrigerator.back.comment.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.comment.adapter.in.dto.response.InCommentListDTO;
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
    public InCommentListDTO findCommentByHeart(
            @PathVariable("recipeId") Long recipeId,
            @RequestParam("page") int page,
            @RequestParam(value = "size", defaultValue = "11") int size){
        return InCommentListDTO.builder()
                .comments(findCommentListUseCase.findCommentsByHeart(recipeId, memberInformation.getMemberEmail(), page, size))
                .build();
    }

    @GetMapping("/api/recipe/{recipeId}/comments/date")
    public InCommentListDTO findCommentByDate(
            @PathVariable("recipeId") Long recipeId,
            @RequestParam("page") int page,
            @RequestParam(value = "size", defaultValue = "11") int size){
        return InCommentListDTO.builder()
                .comments(findCommentListUseCase.findCommentsByDate(recipeId, memberInformation.getMemberEmail(), page, size))
                .build();
    }

    @GetMapping("/api/recipe/{recipeId}/comments/preview")
    public InCommentListDTO findCommentPreview(
            @PathVariable("recipeId") Long recipeId,
            @RequestParam(value = "size", defaultValue = "3") int size){
        return findCommentPreviewListUseCase.findCommentPreviews(recipeId, memberInformation.getMemberEmail(), size);
    }

    @GetMapping("/api/recipe/{recipeId}/comments/my")
    public InCommentListDTO findMyComments(@PathVariable("recipeId") Long recipeId){
        return InCommentListDTO.builder()
                .comments(findMyCommentsUseCase.findMyComments(memberInformation.getMemberEmail(), recipeId))
                .build();
    }
}
