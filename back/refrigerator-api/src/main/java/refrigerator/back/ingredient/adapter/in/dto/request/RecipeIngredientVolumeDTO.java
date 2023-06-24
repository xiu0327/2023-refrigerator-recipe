package refrigerator.back.ingredient.adapter.in.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
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
    private Double volume;

    @NotBlank
    private String unit;
}