package refrigerator.back.comment.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.comment.adapter.in.dto.response.InCommentListDTO;
import refrigerator.back.comment.application.port.in.FindCommentListUseCase;
import refrigerator.back.comment.application.port.in.FindCommentPreviewListUseCase;

@RestController
@RequiredArgsConstructor
public class CommentLookUpController {

    private final FindCommentPreviewListUseCase findCommentPreviewListUseCase;
    private final FindCommentListUseCase findCommentListUseCase;

    @GetMapping("/api/comments/heart")
    public InCommentListDTO findCommentByHeart(
            @RequestParam("recipeId") Long recipeId,
            @RequestParam("page") int page,
            @RequestParam(value = "size", defaultValue = "11") int size){
        return InCommentListDTO.builder()
                .comments(findCommentListUseCase.findCommentsByHeart(recipeId, page, size))
                .build();
    }

    @GetMapping("/api/comments/date")
    public InCommentListDTO findCommentByDate(
            @RequestParam("recipeId") Long recipeId,
            @RequestParam("page") int page,
            @RequestParam(value = "size", defaultValue = "11") int size){
        return InCommentListDTO.builder()
                .comments(findCommentListUseCase.findCommentsByDate(recipeId, page, size))
                .build();
    }

    @GetMapping("/api/comments/preview")
    public InCommentListDTO findCommentPreview(
            @RequestParam("recipeId") Long recipeId,
            @RequestParam(value = "size", defaultValue = "3") int size){
        return findCommentPreviewListUseCase.findCommentPreviews(recipeId, size);
    }
}
