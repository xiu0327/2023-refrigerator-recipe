package refrigerator.back.myscore.application.port.out;

import refrigerator.back.myscore.application.domain.MyRecipeScoreDomain;

import java.util.List;

public interface MyRecipeScoreReadPort {
    MyRecipeScoreDomain getMyRecipeScore(String memberID, Long recipeID);
    List<MyRecipeScoreDomain> getMyRecipeScoreList(String memberID, int page, int size);
    MyRecipeScoreDomain getPersistenceMyRecipeById(Long scoreID);
    List<MyRecipeScoreDomain> getMyRecipeScorePreview(String memberID);
    Integer getMyRecipeScoreCount(String memberID);
}
