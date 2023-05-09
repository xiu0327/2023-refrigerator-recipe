package refrigerator.back.ingredient.adapter.in.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDetailResponseDTO {

    private Long ingredientID;

    private String name;                                // 식재료명

    private IngredientStorageType storage;        // 보관방식

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate expirationDate;                   // 소비기한

    private Long remainDays;                            // 남은일수

    private Double volume;                           // 용량

    private String unit;                        // 용량단위

    private String image;                               // 이미지

}