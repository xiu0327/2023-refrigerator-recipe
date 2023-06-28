package refrigerator.back.ingredient.adapter.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.ingredient.adapter.dto.OutIngredientDTO;
import refrigerator.back.ingredient.adapter.repository.IngredientQueryRepository;
import refrigerator.back.ingredient.adapter.repository.IngredientPersistenceRepository;
import refrigerator.back.ingredient.application.dto.IngredientDetailDTO;
import refrigerator.back.ingredient.application.dto.IngredientDTO;
import refrigerator.back.ingredient.adapter.mapper.OutIngredientMapper;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.IngredientSearchCondition;
import refrigerator.back.ingredient.application.port.out.ingredient.lookUp.FindIngredientPort;
import refrigerator.back.ingredient.application.port.out.ingredient.lookUp.FindIngredientListPort;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static refrigerator.back.ingredient.exception.IngredientExceptionType.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class IngredientLookUpAdapter implements FindIngredientListPort, FindIngredientPort {

    private final IngredientPersistenceRepository ingredientPersistenceRepository;
    private final IngredientQueryRepository ingredientQueryRepository;
    private final OutIngredientMapper mapper;

    @Override
    public Ingredient getIngredient(Long id) {
        return ingredientPersistenceRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_INGREDIENT));
    }

    @Override
    public IngredientDetailDTO getIngredientDetail(Long id) {
        return mapper.toIngredientDetailDto(ingredientQueryRepository.findIngredient(id)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_INGREDIENT)));
    }

    @Override
    public List<Ingredient> getIngredients(String email) {
        return ingredientPersistenceRepository.findByEmailAndDeletedFalse(email);
    }

    @Override
    public List<IngredientDTO> getIngredientList(IngredientSearchCondition condition, int page, int size) {
       return mapper(ingredientQueryRepository.findIngredientList(condition, PageRequest.of(page, size)));
    }

    @Override
    public List<IngredientDTO> getIngredientListOfAll(String email) {
        return mapper(ingredientQueryRepository.findIngredientListOfAll(email));
    }

    @Override
    public List<IngredientDTO> getIngredientListByDeadline(LocalDate date, String email) {
        return mapper(ingredientQueryRepository.findIngredientListByDeadline(date, email));
    }

    private List<IngredientDTO> mapper(List<OutIngredientDTO> ingredientListByDeadline) {
        return ingredientListByDeadline.stream()
                .map(mapper::toIngredientDto)
                .collect(Collectors.toList());
    }
}
