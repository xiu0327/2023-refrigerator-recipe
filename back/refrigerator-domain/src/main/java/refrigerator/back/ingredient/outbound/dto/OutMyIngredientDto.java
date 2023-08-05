package refrigerator.back.ingredient.outbound.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import refrigerator.back.ingredient.application.dto.MyIngredientDto;
import refrigerator.back.ingredient.outbound.mapper.OutIngredientMapper;

@Getter
public class OutMyIngredientDto {

    private Long id;
    private String name;
    private Double volume;
    private String unit;

    @QueryProjection
    public OutMyIngredientDto(Long id, String name, Double volume, String unit) {
        this.id = id;
        this.name = name;
        this.volume = volume;
        this.unit = unit;
    }

    public MyIngredientDto mapping(OutIngredientMapper mapper){
        return mapper.toMyIngredientDto(this, false);
    }
}
