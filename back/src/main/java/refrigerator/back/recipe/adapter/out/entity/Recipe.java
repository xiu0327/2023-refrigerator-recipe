package refrigerator.back.recipe.adapter.out.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "recipe")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long recipeID;

    @Column(name = "recipe_name", nullable = false, length = 60)
    private String recipeName;

    @Column(name = "description", nullable = false, length = 350)
    private String description;

    @Column(name = "cooking_time", nullable = false)
    private Integer cookingTime;

    @Column(name = "kcal", nullable = false)
    private Integer kcal;

    @Column(name = "servings", nullable = false)
    private Integer servings;

    @Column(name = "difficulty", nullable = false, length = 10)
    private String difficulty;

    @Column(name = "recipe_type", nullable = false, length = 20)
    private String recipeType;

    @Column(name = "food_type", nullable = false, length = 20)
    private String recipeFoodType;

    @Column(name = "recipe_category", length = 30)
    private String recipeCategory;

    @Column(name = "main_image", nullable = false)
    private String image;

    @Transient
    private Integer score;

    @Transient
    private Integer person;

    @Transient
    private Integer views;

    @Transient
    private Integer bookmarks;

    @Transient
    private Set<RecipeIngredient> ingredients = new HashSet<>();

    public void init(HashSet<RecipeIngredient> ingredients, Integer score, Integer person, Integer views, Integer bookmarks) {
        this.ingredients = ingredients;
        this.score = score;
        this.person = person;
        this.views = views;
        this.bookmarks = bookmarks;
    }

}
