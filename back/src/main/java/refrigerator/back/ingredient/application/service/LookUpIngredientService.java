package refrigerator.back.ingredient.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.ingredient.adapter.in.dto.response.IngredientDetailResponseDTO;
import refrigerator.back.ingredient.adapter.in.dto.response.IngredientResponseDTO;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.port.in.LookUpIngredientListUseCase;
import refrigerator.back.ingredient.application.port.in.LookUpIngredientDetailUseCase;
import refrigerator.back.ingredient.application.port.in.SearchIngredientListUseCase;
import refrigerator.back.ingredient.application.port.out.ReadIngredient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LookUpIngredientService implements LookUpIngredientListUseCase, LookUpIngredientDetailUseCase, SearchIngredientListUseCase {

    private final ReadIngredient readIngredient;

    @Override
    public List<IngredientResponseDTO> lookUpList(String storage, boolean deadline, String email) {
        return readIngredient.findList(storage, deadline, email);
    }

    @Override
    public List<IngredientResponseDTO> searchList(String name, String email) {
        return readIngredient.findSearchList(name, email);
    }

    @Override
    public IngredientDetailResponseDTO lookUpDetail(Long id, String email) {
        return readIngredient.findIngredient(id, email);
    }

}
