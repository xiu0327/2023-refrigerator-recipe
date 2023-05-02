package refrigerator.back.ingredient.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.ingredient.adapter.out.repository.module.RegisteredIngredientInit;
import refrigerator.back.ingredient.application.domain.RegisteredIngredient;
import refrigerator.back.ingredient.application.port.out.FindRegisteredIngredientPort;
import refrigerator.back.word_completion.application.port.out.FindRegisteredIngredientNameListPort;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RegisteredIngredientAdapter implements FindRegisteredIngredientPort, FindRegisteredIngredientNameListPort {

    private final RegisteredIngredientInit registeredIngredientInit;

    @Override
    public List<RegisteredIngredient> findIngredientList() {
        return registeredIngredientInit.getIngredientList();
    }

    @Override
    public List<String> findIngredientNameList() {
        return registeredIngredientInit.getIngredientList().stream()
                .map(RegisteredIngredient::getName).collect(Collectors.toList());
    }
}
