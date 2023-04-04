package refrigerator.back.comment.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.comment.adapter.in.dto.response.CommentBasicResponseDTO;
import refrigerator.back.comment.application.port.in.heart.AddCommentHeartUseCase;
import refrigerator.back.comment.application.port.in.heart.ReduceCommentHeartUseCase;
import refrigerator.back.comment.application.port.in.people.FindLikedPeopleListUseCase;
import refrigerator.back.global.common.BasicListResponseDTO;
import refrigerator.back.global.common.MemberInformation;

import static refrigerator.back.global.common.MemberInformation.*;

@RestController
@RequiredArgsConstructor
public class CommentHeartController {

    private final AddCommentHeartUseCase addCommentHeartUseCase;
    private final ReduceCommentHeartUseCase reduceCommentHeartUseCase;
    private final FindLikedPeopleListUseCase findLikedPeopleListUseCase;

    @PostMapping("/api/comments/{commentId}/heart/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentBasicResponseDTO addHeartCount(@PathVariable("commentId") Long commentId){
        addCommentHeartUseCase.addHeart(
                getMemberEmail(),
                commentId);
        return new CommentBasicResponseDTO(commentId);
    }

    @PostMapping("/api/comments/{commentId}/heart/reduce")
    public CommentBasicResponseDTO reduceHeartCount(@PathVariable("commentId") Long commentId){
        reduceCommentHeartUseCase.reduceHeart(
                getMemberEmail(),
                commentId);
        return new CommentBasicResponseDTO(commentId);
    }

    @GetMapping("/api/comments/heart/list")
    public BasicListResponseDTO<Long> findLikedPeopleList(){
        return new BasicListResponseDTO<>(
                findLikedPeopleListUseCase.findLikedPeople(getMemberEmail())
        );
    }
}
