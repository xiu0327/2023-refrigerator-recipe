package refrigerator.back.recipe.adapter.in.dto;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
public class InRecipeBasicListDTO<T> implements Serializable {
    List<T> data;
    public InRecipeBasicListDTO(List<T> data) {
        this.data = data;
    }
}
