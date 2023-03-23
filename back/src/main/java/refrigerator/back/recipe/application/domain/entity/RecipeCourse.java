package refrigerator.back.recipe.application.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "recipe_course", indexes = {@Index(name = "recipe_course_index", columnList = "recipe_id")})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_course_id")
    private Long courseID;

    @Column(name = "recipe_id", nullable = false)
    private Long recipeID;

    @Column(name = "step", nullable = false)
    private Integer step;

    @Column(name = "explanation", nullable = false)
    private String explanation;

    @Column(name = "image")
    private String image;

}
