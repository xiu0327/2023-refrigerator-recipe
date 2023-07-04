package refrigerator.back.comment.application.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "recipe_comment")
@Getter
@NoArgsConstructor
@Slf4j
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_comment_id")
    private Long commentId;

    @Column(name = "recipe_id", nullable = false)
    private Long recipeId;

    @Column(name = "member_email", nullable = false)
    private String writerId;

    @Column(name = "content", nullable = false)
    private String content;

    @Embedded
    private CommentRecord commentRecord;

    public Comment(Long recipeId, String writerId, String content, LocalDateTime createDateTime) {
        this.recipeId = recipeId;
        this.content = content;
        this.writerId = writerId;
        this.commentRecord = new CommentRecord(createDateTime);
    }

    public CommentHeart createCommentHeart(){
        if (commentId != null){
            return new CommentHeart(commentId);
        }
        throw new RuntimeException("commentId 가 비어있습니다.");
    }

    public boolean isDeleted(){
        return commentRecord.deletedState;
    }

    public Long edit(String content, LocalDateTime now){
        this.content = content;
        commentRecord.renew(now);
        return commentId;
    }

    public Long delete(){
        commentRecord.enableDeleteStatus();
        return commentId;
    }

}
