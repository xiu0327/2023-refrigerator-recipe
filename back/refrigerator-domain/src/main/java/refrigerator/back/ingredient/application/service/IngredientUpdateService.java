package refrigerator.back.ingredient.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.time.CurrentTime;
import refrigerator.back.ingredient.application.dto.IngredientRegisterDTO;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;
import refrigerator.back.ingredient.application.port.in.ingredient.update.ModifyIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.ingredient.update.RegisterIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.ingredient.update.RemoveIngredientUseCase;
import refrigerator.back.ingredient.application.port.out.ingredient.lookUp.FindIngredientPort;
import refrigerator.back.ingredient.application.port.out.ingredient.update.DeleteIngredientPort;
import refrigerator.back.ingredient.application.port.out.ingredient.update.SaveIngredientPort;


import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class IngredientUpdateService implements RegisterIngredientUseCase, ModifyIngredientUseCase, RemoveIngredientUseCase {

    private final FindIngredientPort findIngredientPort;
    private final DeleteIngredientPort deleteIngredientPort;
    private final SaveIngredientPort saveIngredientPort;
    private final CurrentTime<LocalDate> currentTime;
    
    @Override
    public IngredientRegisterDTO registerIngredient(String name, LocalDate expirationDate, Double capacity,
                                                    String capacityUnit, IngredientStorageType storageMethod, Integer image, String email) {

        return IngredientRegisterDTO.builder()
                .ingredientID(saveIngredientPort.saveIngredient(
                        Ingredient.create(name, expirationDate, currentTime.now(), capacity,
                                    capacityUnit, storageMethod, image, email))).build();
    }

    @Override
    public void modifyIngredient(Long id, LocalDate expirationDate, Double capacity, IngredientStorageType storageMethod) {
        Ingredient ingredient = findIngredientPort.getIngredient(id);
        ingredient.modify(expirationDate, capacity, storageMethod);
        saveIngredientPort.saveIngredient(ingredient);
    }

    @Override
    public Long removeIngredient(Long id) {
        return deleteIngredientPort.deleteIngredient(id);
    }

    @Override
    public Long removeAllIngredients(List<Long> ids) {
        return deleteIngredientPort.deleteAllIngredients(ids);
    }
}
