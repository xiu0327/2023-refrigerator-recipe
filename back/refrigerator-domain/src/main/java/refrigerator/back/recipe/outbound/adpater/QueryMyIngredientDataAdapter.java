package refrigerator.back.recipe.outbound.adpater;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import refrigerator.back.recipe.outbound.repository.query.RecipeSelectQueryRepository;
import refrigerator.back.recipe.application.domain.MyIngredientCollection;
import refrigerator.back.recipe.application.port.out.GetMyIngredientDataPort;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class QueryMyIngredientDataAdapter implements GetMyIngredientDataPort {

    private final RecipeSelectQueryRepository repository;

    @Override
    public MyIngredientCollection getMyIngredients(String memberId) {
        Map<String, Double> myIngredients = new HashMap<>();
        repository.selectMyIngredients(memberId)
                .forEach(data -> myIngredients.put(data.getName(), data.getVolume()));
        return new MyIngredientCollection(myIngredients);
    }
}
