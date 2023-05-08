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

    @Column(name = "url", nullable = false, length = 400)
    private String url;
}
