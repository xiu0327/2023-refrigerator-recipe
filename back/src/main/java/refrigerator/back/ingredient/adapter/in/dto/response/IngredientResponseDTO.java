package refrigerator.back.ingredient.adapter.in.dto.response;


import lombok.*;


@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IngredientResponseDTO {

    private Long ingredientID;
    private String name;
    private String remainDays;
    private String image;
}
