package refrigerator.back.ingredient.adapter.out.module;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import refrigerator.back.ingredient.adapter.out.repository.query.RegisteredIngredientQuery;
import refrigerator.back.ingredient.application.domain.RegisteredIngredient;
import refrigerator.back.ingredient.application.port.out.FindRegisteredIngredientPort;
import refrigerator.back.word_completion.application.port.out.FindRegisteredIngredientNameListPort;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RegisteredIngredientInit implements FindRegisteredIngredientPort, FindRegisteredIngredientNameListPort{

    private final RegisteredIngredientQuery registeredIngredientQuery;
    private List<RegisteredIngredient> ingredients;

    @PostConstruct
    public void init(){
        this.ingredients = registeredIngredientQuery.findIngredientList();
    }

    @Override
    public List<String> findIngredientNameList() {
        return ingredients.stream().map(RegisteredIngredient::getName).collect(Collectors.toList());
    }

    @Override
    public List<RegisteredIngredient> findIngredientList() {
        return ingredients;
    }

}
