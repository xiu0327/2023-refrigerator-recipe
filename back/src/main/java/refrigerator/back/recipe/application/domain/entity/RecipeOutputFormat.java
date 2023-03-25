package refrigerator.back.recipe.application.domain.entity;

public abstract class RecipeOutputFormat {

    private static String changeKcalFormat(int kcal){
        return kcal + "kcal";
    }

    private static String changeServingsFormat(int servings){
        return servings + "인분";
    }

    private static String changeCookingTimeFormat(int cookingTime){
        return cookingTime + "분";
    }

}
