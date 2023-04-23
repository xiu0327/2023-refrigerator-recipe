package refrigerator.back.searchword.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import refrigerator.back.recipe.adapter.in.cache.config.RecipeCacheKey;
import refrigerator.back.searchword.application.domain.Ingredient;
import refrigerator.back.searchword.application.port.in.FindRecommendSearchWordUseCase;
import refrigerator.back.searchword.application.port.out.FindIngredientsByMemberPort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendSearchWordService implements FindRecommendSearchWordUseCase {

    private final FindIngredientsByMemberPort findIngredientsByMemberPort;

    @Override
    public List<String> getRecommendSearchWords(String memberId) {
        List<Ingredient> ingredients = findIngredientsByMemberPort.getIngredients(memberId);
        if (ingredients.size() <= 0){
            return new ArrayList<>();
        }
        return ingredients.stream()
                .filter(Ingredient::isExpired)
                .sorted(Comparator.comparingInt(Ingredient::calculationDDay))
                .map(Ingredient::getName)
                .limit(5)
                .collect(Collectors.toList());
    }

}
