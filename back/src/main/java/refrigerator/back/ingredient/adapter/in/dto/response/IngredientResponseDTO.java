package refrigerator.back.ingredient.adapter.in.dto.response;


import lombok.*;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IngredientResponseDTO {

    private Long ingredientID;
    private String name;        // 식재료명
    private Long remainDays;    // 남은일수
    private String image;       // 이미지
}
