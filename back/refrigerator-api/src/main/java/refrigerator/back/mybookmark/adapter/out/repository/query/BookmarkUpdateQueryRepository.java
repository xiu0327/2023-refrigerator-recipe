package refrigerator.back.mybookmark.adapter.out.repository.query;

public interface BookmarkUpdateQueryRepository {
    void removeBookmarkByRecipeIdAndMemberId(Long recipeId, String memberId);
}
