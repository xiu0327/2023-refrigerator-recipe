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
public class IngredientDetailResponseDTO {

    private Long id;
    private String name;                // 식재료명
    private String storageMethod;       // 보관방식
    private LocalDate expirationDate;   // 소비기한
    private Long remainDays;         // 남은일수
    private Integer capacity;           // 용량
    private String capacityUnit;        // 용량단위
    private String image;               // 이미지

}