package refrigerator.back.recipe.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.recipe.adapter.out.repository.RecipeSelectQueryRepository;
import refrigerator.back.recipe.application.domain.MyIngredientCollection;
import refrigerator.back.recipe.application.port.out.GetMyIngredientDataPort;

import java.util.HashMap;
import java.util.Map;

@Repository
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
