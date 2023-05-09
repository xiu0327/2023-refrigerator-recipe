package refrigerator.back.ingredient.adapter.in.dto.response;


import lombok.*;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IngredientResponseDTO {

    private Long ingredientID;
    private String name;
    private Long remainDays;
    private String image;
}
