package refrigerator.back.ingredient.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import refrigerator.back.global.exception.domain.BusinessException;
import refrigerator.back.ingredient.adapter.out.dto.OutIngredientDTO;
import refrigerator.back.ingredient.application.dto.IngredientDetailDTO;
import refrigerator.back.ingredient.application.dto.IngredientDTO;
import refrigerator.back.ingredient.adapter.out.mapper.OutIngredientMapper;
import refrigerator.back.ingredient.adapter.out.repository.IngredientRepository;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.IngredientSearchCondition;
import refrigerator.back.ingredient.application.domain.SuggestedIngredient;
import refrigerator.back.ingredient.application.port.out.ReadIngredientPort;
import refrigerator.back.ingredient.application.port.out.WriteIngredientPort;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static refrigerator.back.ingredient.exception.IngredientExceptionType.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class IngredientLookupAdapter implements ReadIngredientPort {

    private final IngredientRepository ingredientRepository;
    private final OutIngredientMapper mapper;

    // 테스트용
    @Override
    public Ingredient getIngredientById(Long id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_INGREDIENT));
    }

    @Override
    public Ingredient getIngredient(Long id) {
        return ingredientRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_INGREDIENT));
    }

    @Override
    public IngredientDetailDTO getIngredientDetail(Long id) {
        return mapper.toIngredientDetailDto(ingredientRepository.findIngredient(id)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_INGREDIENT)));
    }

    @Override
    public List<IngredientDTO> getIngredientList(IngredientSearchCondition condition, int page, int size) {
       return mapping(ingredientRepository.findIngredientList(condition, PageRequest.of(page, size)));
    }

    @Override
    public List<IngredientDTO> getIngredientListOfAll(String email) {
        return mapping(ingredientRepository.findIngredientListOfAll(email));
    }

    @Override
    public List<IngredientDTO> getIngredientListByDeadline(LocalDate date, String email) {
        return mapping(ingredientRepository.findIngredientListByDeadline(date, email));
    }

    private List<IngredientDTO> mapping(List<OutIngredientDTO> ingredientListByDeadline) {
        return ingredientListByDeadline.stream()
                .map(mapper::toIngredientDto)
                .collect(Collectors.toList());
    }
}
