package refrigerator.back.ingredient.application.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDeductionDTO {

    private String name;
    private Double volume;
    private String unit;
}
