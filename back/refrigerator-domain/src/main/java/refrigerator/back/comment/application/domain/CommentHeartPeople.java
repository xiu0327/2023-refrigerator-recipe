package refrigerator.back.comment.application.domain;

import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;
import java.util.UUID;

@Getter
@RedisHash
public class CommentHeartPeople {

    @Id
    private String id;

    @Indexed
    private Long commentId;

    @Indexed
    private String memberId;

    public CommentHeartPeople(Long commentId, String memberId) {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.commentId = commentId;
        this.memberId = memberId;
    }
}
