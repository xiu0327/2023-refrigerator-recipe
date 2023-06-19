package refrigerator.back.recipe.application.domain.entity;

import lombok.*;
import refrigerator.back.recipe.application.domain.entity.Recipe;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Field;

@Entity
@Table(name = "recipe_course")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RecipeCourse implements Serializable {

    @Id
    @Column(name = "recipe_course_id")
    private Long courseID;

    @Column(name = "step")
    private Integer step;

    @Column(name = "explanation")
    private String explanation;

    @Column(name = "image")
    private String image;

    @Column(name = "recipe_id")
    private Long recipeId;

    public boolean checkNotNull(){
        try{
            for (Field field : getClass().getDeclaredFields()){
                if (field.get(this) == null){
                    return false;
                }
            }
            return true;
        } catch (IllegalAccessException e) {
            return false;
        }
    }

}
