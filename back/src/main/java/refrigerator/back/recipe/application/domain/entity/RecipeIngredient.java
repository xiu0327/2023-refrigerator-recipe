package refrigerator.back.recipe.application.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "recipe_ingredient", indexes = {@Index(name = "recipe_ingredient_index", columnList = "recipe_id")})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_ingredient_id")
    private Long ingredientID;

    @Column(name = "recipe_id", nullable = false)
    private Long recipeID;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "volume", length = 30)
    private String volume;

    @Column(name = "trans_volume", nullable = false)
    private Double transVolume;

    @Column(name = "trans_unit", length = 20)
    private String transUnit;

    @Column(name = "type", nullable = false, length = 30)
    private String type;

}
