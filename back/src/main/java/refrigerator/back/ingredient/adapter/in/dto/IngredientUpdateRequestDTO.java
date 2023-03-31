package refrigerator.back.ingredient.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientUpdateRequestDTO {
    // 검증 필요 @Validation
    private LocalDate ExpirationDate;       // 유통기한
    private Integer capacity;               // 용량
    private String storageMethod;           // 보관방법
}
