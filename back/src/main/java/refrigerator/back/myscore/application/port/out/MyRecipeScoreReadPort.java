package refrigerator.back.myscore.application.port.out;

import refrigerator.back.myscore.application.domain.MyRecipeScoreDomain;

import java.util.List;

public interface MyRecipeScoreReadPort {
    MyRecipeScoreDomain getMyRecipeScoreByID(Long scoreID);
    MyRecipeScoreDomain getMyRecipeScore(String memberID, Long recipeID);
    List<MyRecipeScoreDomain> getMyRecipeScoreList(String memberID, int page, int size);
}
