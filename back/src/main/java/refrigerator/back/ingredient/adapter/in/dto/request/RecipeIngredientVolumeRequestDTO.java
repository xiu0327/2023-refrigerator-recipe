package refrigerator.back.ingredient.adapter.in.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeIngredientVolumeRequestDTO {

    @Valid
    @NotEmpty
    @Size(min = 1)
    List<RecipeIngredientVolumeDTO> ingredients;
}
