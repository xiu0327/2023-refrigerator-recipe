package refrigerator.back.ingredient.adapter.in.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientUpdateRequestDTO {
    // 검증 필요 @Validation
    private LocalDate ExpirationDate;       // 유통기한
    private Double capacity;               // 용량
    private String storageMethod;           // 보관방법
}
