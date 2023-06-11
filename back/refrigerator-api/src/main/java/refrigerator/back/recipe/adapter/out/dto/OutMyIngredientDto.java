package refrigerator.back.recipe.adapter.out.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class OutMyIngredientDto {
    private String name;
    private Double volume;

    @QueryProjection
    public OutMyIngredientDto(String name, Double volume) {
        this.name = name;
        this.volume = volume;
    }
}
