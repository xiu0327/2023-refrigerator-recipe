package refrigerator.back.comment.application.domain;

import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;

@Getter
@RedisHash
public class CommentHeartPeople {

    @Id
    private String id;

    @Indexed
    private Long commentId;

    @Indexed
    private String memberId;

    public CommentHeartPeople(String id, Long commentId, String memberId) {
        this.id = id;
        this.commentId = commentId;
        this.memberId = memberId;
    }
}
