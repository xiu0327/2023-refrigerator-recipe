package refrigerator.back.ingredient.application.dto;


import lombok.*;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IngredientDTO {

    private Long ingredientID;
    private String name;
    private Integer remainDays;
    private String image;

}
