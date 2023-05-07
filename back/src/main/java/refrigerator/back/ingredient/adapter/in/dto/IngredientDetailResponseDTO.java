package refrigerator.back.ingredient.adapter.in.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDetailResponseDTO {

    private Long ingredientID;
    private String name;                // 식재료명
    private String storage;       // 보관방식
    private LocalDate expirationDate;   // 소비기한
    private Long remainDays;         // 남은일수
    private Integer volume;           // 용량
    private String unit;        // 용량단위
    private Integer image;               // 이미지

}