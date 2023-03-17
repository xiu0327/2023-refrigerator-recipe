package refrigerator.back.recipe.adapter.out.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "recipe_category")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_category_id")
    private Integer categoryID;

    @Column(name = "recipe_category", nullable = false, length = 35)
    private String categoryName;

}
