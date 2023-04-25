package refrigerator.back.ingredient.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.ingredient.application.domain.Ingredient;
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
                                   String capacityUnit, String storageMethod, Integer imageId, String email) {

        return writeIngredientPort.saveIngredient(Ingredient.create(name, expirationDate, capacity,
                                    capacityUnit, storageMethod, imageId, email));
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
    public void modifyIngredient(Long id, LocalDate expirationDate, Double capacity, String storageMethod) {
        Ingredient ingredient = readIngredientPort.getIngredientById(id);
        ingredient.modify(expirationDate, capacity, storageMethod);
        writeIngredientPort.saveIngredient(ingredient);
    }

    @Override
    public void removeIngredient(Long id) {
        Ingredient ingredient = readIngredientPort.getIngredientById(id);
        ingredient.delete();
        writeIngredientPort.saveIngredient(ingredient);
    }

    @Override
    public void removeAllIngredients(List<Long> ids) {
        for (Long id : ids) {
            Ingredient ingredient = readIngredientPort.getIngredientById(id);
            ingredient.delete();
            writeIngredientPort.saveIngredient(ingredient);
        }
    }
}
