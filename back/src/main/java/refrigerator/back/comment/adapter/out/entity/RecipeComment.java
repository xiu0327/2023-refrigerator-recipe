package refrigerator.back.comment.adapter.out.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import refrigerator.back.global.common.BaseTimeEntity;
import refrigerator.back.global.common.BaseTimeEntityWithModify;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recipe_comment")
@Getter
@NoArgsConstructor
public class RecipeComment extends BaseTimeEntityWithModify {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_comment_id")
    private Long commentID;

    @Column(name = "recipe_id")
    private Long recipeID;

    @Column(name = "member_email")
    private String memberID;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "modified_state", nullable = false)
    private boolean modifiedState;

}
