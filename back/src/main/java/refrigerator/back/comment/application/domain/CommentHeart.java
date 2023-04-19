package refrigerator.back.comment.application.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "comment_heart")
@Getter
@NoArgsConstructor
public class CommentHeart {

    @Id
    @Column(name = "comment_id")
    Long commentId;

    @Column(name = "count")
    Integer count;

    @Column(name = "delete_status")
    Boolean deleteStatus;

    public CommentHeart(Long commentId) {
        this.commentId = commentId;
        this.count = 0;
        this.deleteStatus = false;
    }

    /* 비즈니스 로직 */
    public void add(){
        this.count++;
    }
}
