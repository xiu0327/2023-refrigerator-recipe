package refrigerator.back.recipe.adapter.in.cache.config;

public class RecipeCacheKey {
    public static final String FOOD_TYPE = "FOOD_TYPE";
    public static final String CATEGORY = "CATEGORY";
    public static final String RECIPE = "RECIPE";
    public static final String RECIPE_TYPE = "RECIPE_TYPE";
    public static final String RECIPE_DIFFICULTY = "RECIPE_DIFFICULTY";
    public static final int RECIPE_EXPIRE_SEC = 30;
    public static final int CONDITION_EXPIRE_SEC = 24 * 60 * 60;
    public static final int DEFAULT_EXPIRE_SEC = 30 * 30;
}
