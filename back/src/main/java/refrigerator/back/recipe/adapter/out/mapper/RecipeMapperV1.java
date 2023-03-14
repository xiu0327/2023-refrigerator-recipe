//package refrigerator.back.recipe.adapter.out.mapper;
//
//import org.springframework.stereotype.Component;
//import refrigerator.back.recipe.adapter.out.entity.*;
//import refrigerator.back.recipe.application.domain.entity.RecipeCourseDomain;
//import refrigerator.back.recipe.application.domain.entity.RecipeDomain;
//import refrigerator.back.recipe.application.domain.entity.RecipeIngredientDomain;
//import refrigerator.back.recipe.application.domain.value.RecipeDifficulty;
//import refrigerator.back.recipe.application.domain.value.RecipeIngredientType;
//import refrigerator.back.recipe.application.domain.value.RecipeScore;
//import refrigerator.back.recipe.application.domain.value.RecipeType;
//
//@Component
//public class RecipeMapperV1 implements RecipeMapper{
//    @Override
//    public RecipeDomain toRecipeDomain(Recipe entity) {
//        return RecipeDomain.builder()
//                .recipeID(entity.getRecipeID())
//                .cookingTime(entity.getCookingTime())
//                .recipeName(entity.getRecipeName())
//                .description(entity.getDescription())
//                .difficulty(RecipeDifficulty.lookupByLevelName(entity.getDifficulty()))
//                .image(entity.getImages())
//                .score(toRecipeScore(entity.getScore()))
//                .kcal(entity.getKcal())
//                .servings(entity.getServings())
//                .type(toRecipeType(entity.getTypes()))
//                .views(entity.getViews())
//                .bookmarks(entity.getBookmarks())
//                .build();
//    }
//
//    @Override
//    public Recipe toRecipeEntity(RecipeDomain domain) {
//        return Recipe.builder()
//                .recipeID(domain.getRecipeID())
//                .cookingTime(domain.getCookingTime())
//                .recipeName(domain.getRecipeName())
//                .description(domain.getDescription())
//                .difficulty(domain.getDifficulty().getLevelName())
//                .kcal(domain.getKcal())
//                .servings(domain.getServings())
//                .images(domain.getImage())
//                .score(toScore(domain.getRecipeID(), domain.getScore()))
//                .views(domain.getViews())
//                .build();
//    }
//
//    @Override
//    public RecipeCourseDomain toRecipeCourseDomain(RecipeCourse entity) {
//        return RecipeCourseDomain.builder()
//                .courseID(entity.getCourseID())
//                .explanation(entity.getExplanation())
//                .step(entity.getStep())
//                .build();
//    }
//
//    @Override
//    public RecipeIngredientDomain toRecipeIngredientDomain(RecipeIngredient entity) {
//        return RecipeIngredientDomain.builder()
//                .ingredientID(entity.getIngredientID())
//                .name(entity.getName())
//                .volume(entity.getVolume())
//                .type(RecipeIngredientType.lookup(entity.getType()))
//                .build();
//    }
//
//    private RecipeScore toRecipeScore(Score score){
//        return new RecipeScore(
//                score.getPerson(),
//                score.getScore());
//    }
//
//    private Score toScore(Long recipeID, RecipeScore recipeScore){
//        return Score.builder()
//                .recipeID(recipeID)
//                .person(recipeScore.getPerson())
//                .score(recipeScore.getScore()).build();
//    }
//
//    private RecipeType toRecipeType(Types types){
//        return new RecipeType(
//                types.getRecipeType(),
//                types.getFoodType(),
//                types.getRecipeCategory());
//    }
//
//    private Types toTypes(RecipeType recipeType){
//        return Types.builder()
//                .foodType(recipeType.getRecipeFoodType())
//                .recipeCategory(recipeType.getRecipeCategory())
//                .recipeType(recipeType.getRecipeType()).build();
//    }
//}
