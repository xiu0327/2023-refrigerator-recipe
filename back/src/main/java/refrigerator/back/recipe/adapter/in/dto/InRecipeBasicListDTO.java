package refrigerator.back.recipe.adapter.in.dto;

import lombok.Data;

import java.util.List;

@Data
public class InRecipeBasicListDTO<T> {
    List<T> data;

    public InRecipeBasicListDTO(List<T> data) {
        this.data = data;
    }
}
