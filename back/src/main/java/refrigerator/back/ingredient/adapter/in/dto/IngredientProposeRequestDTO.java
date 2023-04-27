package refrigerator.back.ingredient.adapter.in.dto;


import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class IngredientProposeRequestDTO {

    @NotEmpty
    String name;

    @NotEmpty
    String capacityUnit;

}
