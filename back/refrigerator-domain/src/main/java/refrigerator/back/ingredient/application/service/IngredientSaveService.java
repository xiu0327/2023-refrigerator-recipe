package refrigerator.back.ingredient.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.time.CurrentDate;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;
import refrigerator.back.ingredient.application.dto.IngredientRegisterDTO;
import refrigerator.back.ingredient.application.port.in.ingredient.update.RegisterIngredientUseCase;
import refrigerator.back.ingredient.application.port.out.ingredient.lookUp.FindIngredientPort;
import refrigerator.back.ingredient.application.port.out.ingredient.update.DeleteIngredientPort;
import refrigerator.back.ingredient.application.port.out.ingredient.update.SaveIngredientPort;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class IngredientSaveService implements RegisterIngredientUseCase {

    private final SaveIngredientPort saveIngredientPort;

    private final CurrentDate currentDate;

    @Override
    public IngredientRegisterDTO registerIngredient(String name, LocalDate expirationDate, Double capacity,
                                                    String capacityUnit, IngredientStorageType storageMethod, Integer image, String email) {

        return IngredientRegisterDTO.builder()
                .ingredientID(saveIngredientPort.saveIngredient(Ingredient.create(name, expirationDate, currentDate.now(), capacity,
                        capacityUnit, storageMethod, image, email))).build();
    }
}
