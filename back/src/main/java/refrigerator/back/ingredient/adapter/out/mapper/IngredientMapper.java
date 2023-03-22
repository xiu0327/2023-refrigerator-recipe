package refrigerator.back.ingredient.adapter.out.mapper;

import refrigerator.back.ingredient.adapter.out.entity.IngredientEntity;
import refrigerator.back.ingredient.application.domain.IngredientDomain;

public interface IngredientMapper {

    public IngredientDomain toIngredientDomain(IngredientEntity entity);
    public IngredientEntity toIngredientEntity(IngredientDomain domain);
}
