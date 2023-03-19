package refrigerator.back.recipe.adapter.in.web;

public abstract class RecipeOutputFormat {

    protected static String changeKcalFormat(int kcal){
        return kcal + "kcal";
    }

    protected static String changeServingsFormat(int servings){
        return servings + "인분";
    }

    protected static String changeCookingTimeFormat(int cookingTime){
        return cookingTime + "분";
    }

}
