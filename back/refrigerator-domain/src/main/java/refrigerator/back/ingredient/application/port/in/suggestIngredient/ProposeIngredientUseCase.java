package refrigerator.back.ingredient.application.port.in.suggestIngredient;

public interface ProposeIngredientUseCase {

    void proposeIngredient(String name, String capacityUnit, String email);
}
