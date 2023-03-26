package refrigerator.back.ingredient.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.ingredient.adapter.in.dto.response.IngredientDetailResponseDTO;
import refrigerator.back.ingredient.adapter.in.dto.response.IngredientResponseDTO;
import refrigerator.back.ingredient.adapter.mapper.IngredientMapper;
import refrigerator.back.ingredient.adapter.out.dto.OutIngredientDetailResponseDTO;
import refrigerator.back.ingredient.adapter.out.dto.OutIngredientResponseDTO;
import refrigerator.back.ingredient.adapter.out.repository.query.IngredientQueryRepository;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.adapter.out.repository.IngredientRepository;
import refrigerator.back.ingredient.application.port.out.ReadIngredient;
import refrigerator.back.ingredient.application.port.out.WriteIngredient;
import refrigerator.back.ingredient.exception.IngredientExceptionType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class IngredientAdapter implements WriteIngredient, ReadIngredient {

    private final IngredientRepository ingredientRepository;
    private final IngredientQueryRepository ingredientQueryRepository;
    private final IngredientMapper ingredientMapper;

    @Override
    public Ingredient findOne(Long id) {
        Optional<Ingredient> entity = ingredientRepository.findById(id);
        return entity.orElse(null);
    }

    @Override
    public Long save(Ingredient ingredient) {
        ingredientRepository.save(ingredient);
        return ingredient.getId();
    }

    @Override
    public void update(Ingredient ingredient) {
        ingredientRepository.save(ingredient);
    }

    @Override
    public void delete(Ingredient ingredient) {
        ingredientRepository.delete(ingredient);
    }

    @Override
    public List<IngredientResponseDTO> findList(String storage, boolean deadline, String email) {
        return ingredientQueryRepository
                .findList(storage, deadline, email)
                .stream()
                .map(OutDTO -> ingredientMapper.toIngredientDto(OutDTO))
                .collect(Collectors.toList());
    }

    @Override
    public List<IngredientResponseDTO> findSearchList(String name, String email) {
        return ingredientQueryRepository
                .findSearchList(name, email)
                .stream()
                .map(OutDTO -> ingredientMapper.toIngredientDto(OutDTO))
                .collect(Collectors.toList());
    }

    @Override
    public IngredientDetailResponseDTO findIngredient(Long id, String email) {
        OutIngredientDetailResponseDTO OutDTO = ingredientQueryRepository.findByIngredientId(id);
        if(OutDTO.getEmail().equals(email)) {
            return ingredientMapper.toIngredientDetailDto(OutDTO);
        }
        else {
            throw new BusinessException(IngredientExceptionType.TEST_ERROR);
        }
    }
}
