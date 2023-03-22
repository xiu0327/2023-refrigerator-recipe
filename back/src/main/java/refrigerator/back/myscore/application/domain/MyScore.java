package refrigerator.back.myscore.application.domain;

import lombok.*;
import refrigerator.back.global.common.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Table(name = "recipe_score_member")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyScore extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "score_id")
    private Long scoreID;

    @Column(name = "member_email", nullable = false, length = 300)
    private String memberID;

    @Column(name = "recipe_id", nullable = false)
    private Long recipeID;

    @Column(name = "score")
    private Double score;

    public void modify(double score){
        this.score = score;
    }

    public static MyScore create(String memberID, Long recipeID, Double score){
        return MyScore.builder()
                .memberID(memberID)
                .recipeID(recipeID)
                .score(score).build();
    }

}
