package refrigerator.back.mybookmark.application.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.back.global.common.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Table(name = "recipe_bookmark_member")
@Getter
@NoArgsConstructor
public class MyBookmark extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long bookmarkId;

    @Column(name = "member_email")
    private String memberId;

    @Column(name = "recipe_id")
    private Long recipeId;

    @Column(name = "deleted")
    private Boolean deleted;

    public MyBookmark(String memberId, Long recipeId) {
        this.memberId = memberId;
        this.recipeId = recipeId;
    }

    /* 비즈니스 로직 */

    public static MyBookmark create(String memberId, Long recipeId){
        MyBookmark newBookmark = new MyBookmark(memberId, recipeId);
        newBookmark.undeleted();
        return newBookmark;
    }

    public void delete(){
        this.deleted = true;
    }

    public void undeleted(){
        this.deleted = false;
    }

    public boolean isDeleted(){
        return this.deleted;
    }

}
