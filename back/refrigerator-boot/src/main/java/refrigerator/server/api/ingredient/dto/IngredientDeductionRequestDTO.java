package refrigerator.server.api.ingredient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.back.ingredient.application.dto.IngredientDeductionDTO;
import refrigerator.server.api.ingredient.mapper.InIngredientMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDeductionRequestDTO {

    @NotBlank
    private String name;

    @NotNull
    @Positive
    private Double volume;

    @NotBlank
    private String unit;

}
