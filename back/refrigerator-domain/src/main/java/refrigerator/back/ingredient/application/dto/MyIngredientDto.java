package refrigerator.back.ingredient.application.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class MyIngredientDto {
    private Long id;
    private String name;
    private Double volume;
    private String unit;
    private Boolean modifyState;

    @Builder
    public MyIngredientDto(Long id, String name, Double volume, String unit, Boolean modifyState) {
        this.id = id;
        this.name = name;
        this.volume = volume;
        this.unit = unit;
        this.modifyState = modifyState;
    }

    public void deduct(IngredientDeductionDTO recipeIngredient){
        double result = volume - recipeIngredient.getVolume();
        if (unit.equals(recipeIngredient.getUnit())){
            volume = result > 0 ? result : 0.0;
            modifyState = true;
        }
    }

}
