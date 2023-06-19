package refrigerator.server.api.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.comment.application.dto.CommentBasicDTO;
import refrigerator.back.comment.application.port.in.heart.AddCommentHeartUseCase;
import refrigerator.back.comment.application.port.in.heart.ReduceCommentHeartUseCase;
import refrigerator.back.comment.application.port.in.people.FindLikedPeopleListUseCase;
import refrigerator.server.api.global.common.BasicListResponseDTO;
import refrigerator.back.authentication.application.port.in.GetMemberEmailUseCase;
import refrigerator.back.notification.application.port.in.commentHeart.CreateCommentHeartNotificationUseCase;



@RestController
@RequiredArgsConstructor
public class CommentHeartController {

    private final AddCommentHeartUseCase addCommentHeartUseCase;
    private final ReduceCommentHeartUseCase reduceCommentHeartUseCase;
    private final FindLikedPeopleListUseCase findLikedPeopleListUseCase;
    private final CreateCommentHeartNotificationUseCase createCommentHeartNotificationUseCase;
    private final GetMemberEmailUseCase memberInformation;

    @PostMapping("/api/comments/{commentId}/heart/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentBasicDTO addHeartCount(@PathVariable("commentId") Long commentId){

        addCommentHeartUseCase.addHeart(
                memberInformation.getMemberEmail(),
                commentId);

        createCommentHeartNotificationUseCase.createCommentHeartNotification(
                memberInformation.getMemberEmail(),
                commentId);

        return new CommentBasicDTO(commentId);
    }

    @PostMapping("/api/comments/{commentId}/heart/reduce")
    public CommentBasicDTO reduceHeartCount(@PathVariable("commentId") Long commentId){

        reduceCommentHeartUseCase.reduceHeart(
                memberInformation.getMemberEmail(),
                commentId);

        return new CommentBasicDTO(commentId);
    }

    @GetMapping("/api/comments/heart/list")
    public BasicListResponseDTO<Long> findLikedPeopleList(){
        return new BasicListResponseDTO<>(
                findLikedPeopleListUseCase.findLikedPeople(memberInformation.getMemberEmail())
        );
    }
}
