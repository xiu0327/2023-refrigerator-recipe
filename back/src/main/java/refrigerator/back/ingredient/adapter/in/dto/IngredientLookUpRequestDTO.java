package refrigerator.back.ingredient.adapter.in.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class IngredientLookUpRequestDTO {

    @NotEmpty
    String storage;

    @NotEmpty
    boolean deadline;
}
