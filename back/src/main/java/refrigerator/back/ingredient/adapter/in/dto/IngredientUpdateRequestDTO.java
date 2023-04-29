package refrigerator.back.ingredient.adapter.in.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientUpdateRequestDTO {

    @NotEmpty
    private LocalDate expirationDate;       // 유통기한

    @NotEmpty
    private Double capacity;                // 용량

    @NotEmpty
    private String storageMethod;           // 보관방법
}
