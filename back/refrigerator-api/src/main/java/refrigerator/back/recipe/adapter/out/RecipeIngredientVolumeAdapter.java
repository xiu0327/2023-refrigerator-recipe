package refrigerator.back.recipe.adapter.out;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.recipe.adapter.in.dto.InRecipeIngredientVolumeDTO;
import refrigerator.back.recipe.adapter.mapper.RecipeDtoMapper;
import refrigerator.back.recipe.adapter.out.dto.QOutRecipeIngredientVolumeDTO;
import refrigerator.back.recipe.application.port.out.FindRecipeIngredientVolumePort;


import java.util.List;
import java.util.stream.Collectors;

import static refrigerator.back.recipe.application.domain.entity.QRecipeIngredient.recipeIngredient;


@Repository
@RequiredArgsConstructor
public class RecipeIngredientVolumeAdapter implements FindRecipeIngredientVolumePort {

    private final JPAQueryFactory jpaQueryFactory;
    private final RecipeDtoMapper mapper;

    @Override
    public List<InRecipeIngredientVolumeDTO> getRecipeIngredientVolumes(Long recipeId) {
        return jpaQueryFactory.select(new QOutRecipeIngredientVolumeDTO(
                recipeIngredient.ingredientID,
                recipeIngredient.name,
                recipeIngredient.transVolume,
                recipeIngredient.transUnit, recipeIngredient.type))
                .from(recipeIngredient)
                .where(recipeIngredient.recipeID.eq(recipeId))
                .fetch()
                .stream().map(mapper::toInRecipeIngredientVolumeDTO)
                .collect(Collectors.toList());
    }

}
