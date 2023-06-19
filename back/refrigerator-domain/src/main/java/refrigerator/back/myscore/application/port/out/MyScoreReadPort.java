package refrigerator.back.myscore.application.port.out;

import refrigerator.back.myscore.application.dto.InMyScoreDTO;
import refrigerator.back.myscore.application.dto.InMyScoreListDTO;
import refrigerator.back.myscore.application.dto.InMyScorePreviewDTO;
import refrigerator.back.myscore.application.domain.MyScore;

import java.util.List;
import java.util.Optional;

public interface MyScoreReadPort {
    Optional<MyScore> findByMemberIdAndRecipeId(String memberID, Long recipeID);
    List<InMyScoreDTO> getMyScoreList(String memberID, int page, int size);
    InMyScoreListDTO<InMyScorePreviewDTO> getMyScorePreview(String memberID, int size);
    Optional<MyScore> findById(Long scoreID);
}
