package refrigerator.back.ingredient.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import refrigerator.back.global.exception.domain.BusinessException;
import refrigerator.back.ingredient.adapter.out.dto.OutIngredientDTO;
import refrigerator.back.ingredient.adapter.out.mapper.OutIngredientMapper;
import refrigerator.back.ingredient.adapter.out.repository.IngredientRepository;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.IngredientSearchCondition;
import refrigerator.back.ingredient.application.domain.SuggestedIngredient;
import refrigerator.back.ingredient.application.dto.IngredientDTO;
import refrigerator.back.ingredient.application.dto.IngredientDetailDTO;
import refrigerator.back.ingredient.application.port.out.ReadIngredientPort;
import refrigerator.back.ingredient.application.port.out.WriteIngredientPort;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static refrigerator.back.ingredient.exception.IngredientExceptionType.NOT_FOUND_INGREDIENT;

@Repository
@RequiredArgsConstructor
@Slf4j
public class IngredientUpdateAdapter implements WriteIngredientPort {

    private final IngredientRepository ingredientRepository;

    @Override
    public Long saveIngredient(Ingredient ingredient) {
        ingredientRepository.save(ingredient);
        return ingredient.getId();
    }

    @Override
    public void proposeIngredient(SuggestedIngredient ingredient) {
        ingredientRepository.saveSuggestIngredient(ingredient);
    }

    @Override
    public void deleteIngredient(Long id) {
        ingredientRepository.deleteIngredient(id);
    }

    @Override
    public void deleteAllIngredients(List<Long> ids) {
        ingredientRepository.deleteAllIngredients(ids);
    }

}
