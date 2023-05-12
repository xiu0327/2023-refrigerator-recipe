package refrigerator.back.ingredient.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;
import refrigerator.back.ingredient.application.domain.SuggestedIngredient;
import refrigerator.back.ingredient.application.port.in.ModifyIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.RemoveIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.RegisterIngredientUseCase;
import refrigerator.back.ingredient.application.port.out.ReadIngredientPort;
import refrigerator.back.ingredient.application.port.out.WriteIngredientPort;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class IngredientUpdateService implements RegisterIngredientUseCase, ModifyIngredientUseCase, RemoveIngredientUseCase {

    private final WriteIngredientPort writeIngredientPort;
    private final ReadIngredientPort readIngredientPort;

    @Override
    public Long registerIngredient(String name, LocalDate expirationDate, Double capacity,
                                   String capacityUnit, IngredientStorageType storageMethod, Integer image, String email) {

        return writeIngredientPort.saveIngredient(Ingredient.create(name, expirationDate, capacity,
                                    capacityUnit, storageMethod, image, email));
    }

    @Override
    public void proposeIngredient(String name, String capacityUnit, String email) {
        writeIngredientPort.proposeIngredient(
                SuggestedIngredient.builder()
                .name(name)
                .unit(capacityUnit)
                .email(email)
                .build()
        );
    }

    @Override
    public void modifyIngredient(Long id, LocalDate expirationDate, Double capacity, IngredientStorageType storageMethod) {
        Ingredient ingredient = readIngredientPort.getIngredient(id);
        ingredient.modify(expirationDate, capacity, storageMethod);
        writeIngredientPort.saveIngredient(ingredient);
    }

    @Override
    public void removeIngredient(Long id) {
        writeIngredientPort.deleteIngredient(id);
    }

    @Override
    public void removeAllIngredients(List<Long> ids) {
        writeIngredientPort.deleteAllIngredients(ids);
    }
}
