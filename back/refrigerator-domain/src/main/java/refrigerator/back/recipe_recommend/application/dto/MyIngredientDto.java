package refrigerator.back.recipe_recommend.application.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class MyIngredientDto {

    private String name;
    private Double volume;
    private String unit;

}
