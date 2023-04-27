package refrigerator.back.comment.application.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "comment_heart_people")
@Getter
@NoArgsConstructor
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
        return memberId + "_" + commentId;
    }
}