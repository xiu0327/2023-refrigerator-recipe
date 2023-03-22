package refrigerator.back.ingredient.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.ingredient.application.domain.IngredientDomain;
import refrigerator.back.ingredient.application.port.in.ModifyIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.RemoveIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.RegisterIngredientUseCase;
import refrigerator.back.ingredient.application.port.out.ReadIngredient;
import refrigerator.back.ingredient.application.port.out.WriteIngredient;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class IngredientService implements RegisterIngredientUseCase, ModifyIngredientUseCase, RemoveIngredientUseCase {

    private final WriteIngredient writeIngredient;
    private final ReadIngredient readIngredient;

    @Override
    public Long register(String name, LocalDateTime expirationDate, String capacity, String capacityUnit, String storageMethod, String email) {
        return writeIngredient.save(IngredientDomain.register(name, expirationDate, capacity, capacityUnit, storageMethod, email));
        
        // 도메인 등록
    }

    @Override
    public void modify(Long ingredientId, LocalDateTime expirationDate, String capacity, String storageMethod, String email) {
        IngredientDomain ingredient = readIngredient.findOne(ingredientId);
        ingredient.modify(expirationDate, capacity, storageMethod);
        writeIngredient.update(ingredient);

        // 도메인 수정
    }

    @Override
    public void remove(Long id, String email) {
        writeIngredient.delete(id, email);
    }
}
