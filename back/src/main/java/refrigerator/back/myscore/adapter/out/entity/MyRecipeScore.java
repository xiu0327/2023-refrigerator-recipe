package refrigerator.back.myscore.adapter.out.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recipe_score_member")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyRecipeScore {

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

}
