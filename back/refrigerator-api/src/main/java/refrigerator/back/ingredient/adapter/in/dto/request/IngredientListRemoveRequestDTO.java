package refrigerator.back.ingredient.adapter.in.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class IngredientListRemoveRequestDTO {

    @NotEmpty
    @Size(min = 1)
    List<Long> removeIds;
}
