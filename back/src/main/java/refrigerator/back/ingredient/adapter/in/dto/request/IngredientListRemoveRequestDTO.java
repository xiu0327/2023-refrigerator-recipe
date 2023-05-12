package refrigerator.back.ingredient.adapter.in.dto.request;


import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
