package refrigerator.back.mybookmark.application.port.out;

public interface RemoveBookmarkByRecipeIdPort {
    void removeByRecipeId(Long recipeId, String memberId);
}
