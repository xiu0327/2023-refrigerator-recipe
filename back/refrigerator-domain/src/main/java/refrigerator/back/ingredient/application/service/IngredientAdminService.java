package refrigerator.back.ingredient.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refrigerator.back.ingredient.application.domain.RegisteredIngredient;
import refrigerator.back.ingredient.application.port.admin.AddConfirmIngredientUseCase;
import refrigerator.back.ingredient.application.port.admin.FindUnitNamePort;
import refrigerator.back.ingredient.application.port.admin.SaveRegisteredIngredientPort;
import refrigerator.back.ingredient.application.port.admin.SendIngredientNamePort;

@Service
@RequiredArgsConstructor
public class IngredientAdminService implements AddConfirmIngredientUseCase {

    private final FindUnitNamePort findUnitNamePort;
    private final SaveRegisteredIngredientPort saveRegisteredIngredientPort;
    private final SendIngredientNamePort sendIngredientNamePort;

    @Override
    public void addConfirmIngredient(String name, Integer image) {

        String unitName = findUnitNamePort.findUnitName(name);
        saveRegisteredIngredientPort.saveRegisteredIngredient(
                RegisteredIngredient.create(name, unitName, image));
        sendIngredientNamePort.sendIngredientName(name);
    }
}
