package refrigerator.back.comment.application.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Entity
@Table(name = "comment_heart_people")
@Getter
@NoArgsConstructor
@Slf4j
public class CommentHeartPeople {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_heart_people_id")
    private Long id;

    @Column(name = "member_email", nullable = false)
    String memberId;

    @Column(name = "comment_id", nullable = false)
    Long commentId;

    @Column(name = "delete_status", nullable = false)
    Boolean deleteStatus;

    public CommentHeartPeople(String memberId, Long commentId) {
        this.memberId = memberId;
        this.commentId = commentId;
        this.deleteStatus = false;
    }

    public String getSubPeopleId(){
        return makeSubPeopleId(memberId, commentId);
    }

    public static String makeSubPeopleId(String memberId, Long commentId){
        int commentHash = 7;
        log.info("memberId = {}, commentId = {}", memberId, commentId);
        log.info("memberId hashCode = {}", memberId.hashCode());
        String result = String.valueOf(31 * commentHash * commentId * memberId.hashCode());
        log.info("result = {}", result);
        return result;
    }
}
