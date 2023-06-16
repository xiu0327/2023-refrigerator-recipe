package refrigerator.back.recipe.application.port.out;

public interface CheckMemberBookmarkedStatusPort {
    Boolean getStatus(Long recipeId, String memberId);
}
