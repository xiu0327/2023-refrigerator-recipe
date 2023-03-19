package refrigerator.back.myscore.application.port.out;

import refrigerator.back.myscore.adapter.out.entity.MyRecipeScore;
import refrigerator.back.myscore.application.domain.MyRecipeScoreDomain;

public interface MyRecipeScoreWritePort {
    Long save(MyRecipeScoreDomain domain);
}
