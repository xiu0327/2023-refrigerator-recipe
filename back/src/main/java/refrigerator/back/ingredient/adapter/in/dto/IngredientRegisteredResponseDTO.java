package refrigerator.back.ingredient.adapter.in.dto;

import lombok.*;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientRegisteredResponseDTO {

    private Long id;
    private String name;
    private String unit;
    private String image;
}
