package refrigerator.back.ingredient.adapter.dto;

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

    // 검증을 아마 해야할 듯.. @validation

    private String name;                    // 식재료명
    private LocalDateTime expirationDate;   // 유통기한
    private String capacity;                // 용량
    private String capacityUnit;            // 용량 단위
    private String storageMethod;           // 보관 방식
}
