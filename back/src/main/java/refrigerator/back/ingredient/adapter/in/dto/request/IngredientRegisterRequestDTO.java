package refrigerator.back.ingredient.adapter.in.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientRegisterRequestDTO {
    // 검증 필요 @Validation
    private String name;                    // 식재료명
    private LocalDateTime expirationDate;   // 유통기한
    private String capacity;                // 용량
    private String capacityUnit;            // 용량 단위
    private String storageMethod;           // 보관 방식
}


