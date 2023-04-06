package refrigerator.back.ingredient.adapter.in.dto;


import lombok.*;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class IngredientProposeRequestDTO {

    String name;
    String capacityUnit;

}
