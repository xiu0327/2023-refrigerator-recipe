package refrigerator.back.myscore.application.port.in;

import refrigerator.back.myscore.application.domain.MyRecipeScoreDomain;

import java.util.List;

public interface FindMyRecipeScoreListUseCase {
    List<MyRecipeScoreDomain> findMyScoreList(String memberID, int page, int size);
}
