package refrigerator.back.recipe.application.service;

public class RecipeFormatService {
    public String changeKcalFormat(int kcal){
        return kcal + "kcal";
    }

    public String changeServingsFormat(int servings){
        return servings + "인분";
    }

    public String changeCookingTimeFormat(int cookingTime){
        return cookingTime + "분";
    }
}
