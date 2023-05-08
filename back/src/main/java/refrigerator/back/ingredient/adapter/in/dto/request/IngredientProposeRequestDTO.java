package refrigerator.back.ingredient.adapter.in.dto.request;


import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class IngredientProposeRequestDTO {

    @NotBlank
    String name;

    @NotBlank
    String capacityUnit;

}
