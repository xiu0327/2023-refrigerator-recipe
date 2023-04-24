package refrigerator.back.recipe.adapter.out.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OutRecipeIngredientVolumeDTO {
    private String name;
    private Double volume;
    private String unit;

    @QueryProjection
    public OutRecipeIngredientVolumeDTO(String name, Double volume, String unit) {
        this.name = name;
        this.volume = volume;
        this.unit = unit;
    }
}
