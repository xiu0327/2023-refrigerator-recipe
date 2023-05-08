package refrigerator.back.ingredient.adapter.in.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredientVolumeDTO {

    @NotBlank
    private String name;

    @NotNull
    @Positive
    private Double capacity;

    @NotBlank
    private String unit;
}
