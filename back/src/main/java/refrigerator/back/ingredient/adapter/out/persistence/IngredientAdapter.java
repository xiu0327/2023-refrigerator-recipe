package refrigerator.back.ingredient.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.ingredient.adapter.in.dto.IngredientDetailResponseDTO;
import refrigerator.back.ingredient.adapter.in.dto.IngredientRegisteredResponseDTO;
import refrigerator.back.ingredient.adapter.in.dto.IngredientResponseDTO;
import refrigerator.back.ingredient.adapter.mapper.IngredientMapper;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.adapter.out.repository.IngredientRepository;
import refrigerator.back.ingredient.application.domain.IngredientSearchCondition;
import refrigerator.back.ingredient.application.domain.SuggestedIngredient;
import refrigerator.back.ingredient.application.port.out.ReadIngredientPort;
import refrigerator.back.ingredient.application.port.out.WriteIngredientPort;
import refrigerator.back.ingredient.exception.IngredientExceptionType;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class IngredientAdapter implements WriteIngredientPort, ReadIngredientPort {

    private final IngredientRepository ingredientRepository;
    private final IngredientMapper mapper;

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
    public Ingredient getIngredientById(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id).orElse(null);

        if (ingredient != null){
            return ingredient;
        }
        else {
            throw new BusinessException(IngredientExceptionType.NOT_FOUND_INGREDIENT);
        }

    }

    public IngredientDetailResponseDTO getIngredientDetail(Long id) {
        try {
            Ingredient ingredient = ingredientRepository
                    .findByIdAndDeletedFalse(id)
                    .orElse(null);

            return mapper.toIngredientDetailDto(ingredient, ingredient.getRemainDays());

        } catch (RuntimeException e) {
            throw new BusinessException(IngredientExceptionType.NOT_FOUND_INGREDIENT);
        }
    }

    @Override
    public List<IngredientResponseDTO> getIngredientList(IngredientSearchCondition condition, int page, int size) {
       return ingredientRepository.findIngredientList(condition, PageRequest.of(page, size))
               .stream()
               .map(ingredient ->  mapper.toIngredientDto(ingredient, ingredient.getRemainDays()))
               .collect(Collectors.toList());
    }

    @Override
    public List<IngredientResponseDTO> getIngredientListOfAll(String email) {
        return ingredientRepository.findByEmailAndDeletedFalseOrderByNameAsc(email)
                .stream()
                .map(ingredient ->  mapper.toIngredientDto(ingredient, ingredient.getRemainDays()))
                .collect(Collectors.toList());
    }

    @Override
    public List<IngredientRegisteredResponseDTO> getIngredientListOfRegistered() {
        return ingredientRepository.findRegisteredIngredient()
                .stream()
                .map(mapper::toIngredientRegisteredResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<IngredientResponseDTO> getIngredientListByDeadline(LocalDate date, String email) {
        return ingredientRepository.findByExpirationDateAndEmail(date, email)
                .stream()
                .map(ingredient ->  mapper.toIngredientDto(ingredient, ingredient.getRemainDays()))
                .collect(Collectors.toList());
    }
}
