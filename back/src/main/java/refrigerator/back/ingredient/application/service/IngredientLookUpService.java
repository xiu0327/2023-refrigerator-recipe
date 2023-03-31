package refrigerator.back.ingredient.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.ingredient.adapter.in.dto.IngredientDetailResponseDTO;
import refrigerator.back.ingredient.adapter.in.dto.IngredientRegisteredResponseDTO;
import refrigerator.back.ingredient.adapter.in.dto.IngredientResponseDTO;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.IngredientSearchCondition;
import refrigerator.back.ingredient.application.port.in.FindIngredientListUseCase;
import refrigerator.back.ingredient.application.port.in.FindIngredientDetailUseCase;
import refrigerator.back.ingredient.application.port.out.ReadIngredient;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class IngredientLookUpService implements FindIngredientListUseCase, FindIngredientDetailUseCase {

    private final ReadIngredient readIngredient;

    @Override
    public List<IngredientResponseDTO> getIngredientList(IngredientSearchCondition condition, int page, int size) {
        return readIngredient.getIngredientList(condition, page, size);
    }

    @Override
    public List<IngredientResponseDTO> getIngredientListOfAll(String email) {
        return readIngredient.getIngredientListOfAll(email);
    }

    @Override
    public List<IngredientRegisteredResponseDTO> getIngredientListOfRegistered() {
        return readIngredient.getIngredientListOfRegistered();
    }

    @Override
    public IngredientDetailResponseDTO getIngredient(Long id) {
        return readIngredient.getIngredientDetail(id);
    }
}
