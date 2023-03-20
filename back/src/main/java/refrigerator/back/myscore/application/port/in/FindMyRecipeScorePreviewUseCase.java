package refrigerator.back.myscore.application.port.in;

import refrigerator.back.myscore.application.domain.MyRecipeScoreListDomain;

public interface FindMyRecipeScorePreviewUseCase {
    MyRecipeScoreListDomain findPreview(String memberID, int size);
}
