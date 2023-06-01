package refrigerator.back.recipe.adapter.out.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OutRecipeIngredientVolumeDTO {
    private Long id;
    private String name;
    private Double volume;
    private String unit;
    private String type;

    @QueryProjection
    public OutRecipeIngredientVolumeDTO(Long id, String name, Double volume, String unit, String type) {
        this.id = id;
        this.name = name;
        this.volume = volume;
        this.unit = unit;
        this.type = type;
    }
}
