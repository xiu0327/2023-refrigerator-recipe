package refrigerator.back.ingredient.adapter.in.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientResponseDTO {

    private Long id;
    private String name;        // 식재료명
    private Long remainDays;    // 남은일수
    private String image;       // 이미지
}
