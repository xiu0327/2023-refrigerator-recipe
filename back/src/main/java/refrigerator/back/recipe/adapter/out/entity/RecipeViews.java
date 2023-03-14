package refrigerator.back.recipe.adapter.out.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "recipe_views")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeViews {

    @Id
    @Column(name = "recipe_id")
    private Long recipeID;

    @Column(name = "views")
    private int views;

    public void addViews(){
        this.views++;
    }

}
