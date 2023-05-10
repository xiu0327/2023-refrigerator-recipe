package refrigerator.back.ingredient.application.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ingredient_image")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IngredientImage {
    // 사용할 지 모름
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_image_id")
    private Integer id;

    @Column(name = "ingredient_image_type_name", nullable = false, length = 80)
    private String typeName;

    @Column(name = "ingredient_image_file_name", nullable = false)
    private String imageFileName;

}
