package refrigerator.back.recipe.application.domain.entity;

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
    private Double score;

    @Version
    private Long version;

    public double calculateScore() {
        if (person > 0){
            return score / person;
        }
        return 0.0;
    }

}
