package refrigerator.back.recipe_search.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import refrigerator.back.recipe.application.domain.entity.RecipeCategory;
import refrigerator.back.recipe.application.domain.entity.RecipeFoodType;
import refrigerator.back.recipe.infra.redis.config.RecipeCacheKey;
import refrigerator.back.recipe_search.adapter.out.repository.RecipeSearchSelectQueryRepository;
import refrigerator.back.recipe_search.application.port.out.GetSearchConditionDataPort;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RecipeSearchConditionAdapter implements GetSearchConditionDataPort {

    private final RecipeSearchSelectQueryRepository repository;

    @Override
    @Cacheable(value = RecipeCacheKey.FOOD_TYPE,
            key="'condition_food_type'",
            cacheManager = "recipeFoodTypeCacheManager")
    public List<String> findRecipeFoodTypeCond() {
        return repository.findRecipeFoodTypeList()
                .stream().map(RecipeFoodType::getTypeName)
                .collect(Collectors.toList());
    }

    @Cacheable(value = RecipeCacheKey.CATEGORY,
            key="'condition_category'",
            cacheManager = "recipeCategoryCacheManager")
    @Override
    public List<String> findRecipeCategoryCond() {
        return repository.findRecipeCategoryList()
                .stream().map(RecipeCategory::getCategoryName)
                .collect(Collectors.toList());
    }

}
