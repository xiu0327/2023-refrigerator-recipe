package refrigerator.back.myscore.application.port.in;

import refrigerator.back.myscore.application.domain.MyRecipeScoreListDomain;

public interface FindMyRecipeScoreListUseCase {
    MyRecipeScoreListDomain findMyScoreList(String memberID, int page, int size);
}
