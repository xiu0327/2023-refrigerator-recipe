package refrigerator.back.mybookmark.application.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.back.global.common.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recipe_bookmark_member")
@NoArgsConstructor
public class MyBookmark{

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long bookmarkId;

    @Column(name = "member_email")
    private String memberId;

    @Column(name = "recipe_id")
    private Long recipeId;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "create_date")
    private LocalDateTime createDateTime;

    private MyBookmark(String memberId, Long recipeId, LocalDateTime createDateTime) {
        this.memberId = memberId;
        this.recipeId = recipeId;
        this.createDateTime = createDateTime;
        this.deleted = false;
    }

    public MyBookmark(String memberId, Long recipeId, Boolean deleted, LocalDateTime createDateTime) {
        this.memberId = memberId;
        this.recipeId = recipeId;
        this.deleted = deleted;
        this.createDateTime = createDateTime;
    }

    public static MyBookmark create(String memberId, Long recipeId, LocalDateTime createDateTime){
        return new MyBookmark(memberId, recipeId, createDateTime);
    }

}
