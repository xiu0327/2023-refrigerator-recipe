package refrigerator.back.comment.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "comment_heart")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentHeart {

    @Id
    @Column(name = "comment_id")
    Long commentId;

    @Column(name = "count")
    Integer count;

    /* 비즈니스 로직 */
    public void add(){
        this.count++;
    }
}
