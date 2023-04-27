package refrigerator.back.ingredient.adapter.in.dto;


import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class IngredientListRemoveRequestDTO {

    @NotEmpty
    List<Long> removeIds;
}
