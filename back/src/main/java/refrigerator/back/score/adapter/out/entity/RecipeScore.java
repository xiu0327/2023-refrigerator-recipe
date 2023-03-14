package refrigerator.back.score.adapter.out.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "recipe_score")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeScore {

    @Id
    @Column(name = "recipe_id")
    private Long recipeID;

    @Column(name = "person", nullable = false)
    private int person;

    @Column(name = "score", nullable = false)
    private int score;

}
