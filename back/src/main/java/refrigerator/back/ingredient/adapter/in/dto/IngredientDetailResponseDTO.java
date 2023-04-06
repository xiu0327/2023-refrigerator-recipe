package refrigerator.back.ingredient.adapter.in.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDetailResponseDTO {

    private Long id;
    private String name;                // 식재료명
    private String storageMethod;       // 보관방식
    private LocalDate expirationDate;   // 소비기한
    private Long wholeDays;          // 전체일수
    private Long remainDays;         // 남은일수
    private Integer capacity;           // 용량
    private String capacityUnit;        // 용량단위
    private String image;               // 이미지

}