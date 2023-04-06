package refrigerator.back.ingredient.adapter.in.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
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
    private String image;                   // 사진 (수정 가능성)
}


