package refrigerator.back.ingredient.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.ingredient.adapter.out.entity.IngredientEntity;
import refrigerator.back.ingredient.adapter.out.mapper.IngredientMapper;
import refrigerator.back.ingredient.adapter.out.repository.IngredientRepository;
import refrigerator.back.ingredient.application.domain.IngredientDomain;
import refrigerator.back.ingredient.application.port.out.ReadIngredient;
import refrigerator.back.ingredient.application.port.out.WriteIngredient;

import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class IngredientAdapter implements WriteIngredient, ReadIngredient {

    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;

    @Override
    public IngredientDomain findOne(Long id) {
        Optional<IngredientEntity> entity = ingredientRepository.findById(id);
        return entity.map(ingredientMapper::toIngredientDomain).orElse(null);
    }

    @Override
    public Long save(IngredientDomain domain) {
        IngredientEntity entity = ingredientMapper.toIngredientEntity(domain);
        ingredientRepository.save(entity);
        return entity.getIngredientId();
    }

    @Override
    public void update(IngredientDomain domain) {
        IngredientEntity entity = ingredientMapper.toIngredientEntity(domain);
        ingredientRepository.save(entity);
    }

    @Override
    public void delete(Long id, String email) {
        //ingredientRepository.delete();
    }
}
