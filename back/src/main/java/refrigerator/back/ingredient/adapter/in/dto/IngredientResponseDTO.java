package refrigerator.back.ingredient.adapter.in.dto;


import lombok.*;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientResponseDTO {

    private Long id;
    private String name;        // 식재료명
    private Long remainDays;    // 남은일수
    private String image;       // 이미지
}
