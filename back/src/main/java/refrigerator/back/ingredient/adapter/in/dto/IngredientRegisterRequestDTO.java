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
public class IngredientRegisterRequestDTO {
    // 검증 필요 @Validation
    private String name;                    // 식재료명
    private LocalDate expirationDate;       // 유통기한
    private Integer capacity;               // 용량
    private String capacityUnit;            // 용량 단위
    private String storageMethod;           // 보관 방식
    private Integer imageId;                // 사진 (수정 가능성)
}


