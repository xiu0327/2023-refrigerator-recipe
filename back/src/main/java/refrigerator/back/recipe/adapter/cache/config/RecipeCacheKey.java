package refrigerator.back.recipe.adapter.cache.config;

public class RecipeCacheKey {
    public static final String FOOD_TYPE = "FOOD_TYPE";
    public static final String CATEGORY = "CATEGORY";
    public static final String RECIPE = "RECIPE";
    public static final int RECIPE_EXPIRE_SEC = 30;
    public static final int CONDITION_EXPIRE_SEC = 24 * 60 * 60;
    public static final int DEFAULT_EXPIRE_SEC = 30 * 30;
}
