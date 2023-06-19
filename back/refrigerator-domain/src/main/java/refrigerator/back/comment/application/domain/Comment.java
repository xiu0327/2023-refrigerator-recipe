package refrigerator.back.comment.application.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.back.comment.exception.CommentExceptionType;
import refrigerator.back.global.common.BaseTimeEntityWithModify;
import refrigerator.back.global.exception.BusinessException;

import javax.persistence.*;

import static refrigerator.back.comment.exception.CommentExceptionType.*;


@Entity
@Table(name = "recipe_comment")
@Getter
@NoArgsConstructor
public class Comment extends BaseTimeEntityWithModify {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_comment_id")
    private Long commentID;

    @Column(name = "recipe_id", nullable = false)
    private Long recipeID;

    @Column(name = "member_email", nullable = false)
    private String memberID;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "modified_state", nullable = false)
    private Boolean modifiedState;

    @Column(name = "deleted_state")
    private Boolean deletedState;

    @Builder
    public Comment(Long recipeID, String memberID, String content) {
        this.recipeID = recipeID;
        this.content = content;
        this.memberID = memberID;
        this.modifiedState = false;
        this.deletedState = false;
    }

    /* 비즈니스 로직 */
    public static Comment write(Long recipeID, String memberID, String content){
        return Comment.builder()
                .recipeID(recipeID)
                .memberID(memberID)
                .content(content)
                .build();
    }

    public void edit(String content){
        this.content = content;
        this.modifiedState = true;
    }

    public void delete(){
        this.deletedState = true;
    }

    public void isEqualsAuthor(String memberId){
        if (!memberID.equals(memberId)){
            throw new BusinessException(NO_EDIT_RIGHTS);
        }
    }
}
