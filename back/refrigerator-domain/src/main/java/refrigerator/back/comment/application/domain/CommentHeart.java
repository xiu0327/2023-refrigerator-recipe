package refrigerator.back.comment.application.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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

    public boolean isDeleted(){
        return deleteStatus;
    }

}
