package refrigerator.back.myscore.adapter.out.repository.query;

import org.springframework.data.domain.Pageable;
import refrigerator.back.myscore.adapter.out.dto.OutMyScoreDTO;
import refrigerator.back.myscore.adapter.out.dto.OutMyScorePreviewDTO;

import java.util.List;

public interface MyScoreQueryRepository {
    List<OutMyScoreDTO> findMyRecipeScoreList(String memberID, Pageable pageable);
    List<OutMyScorePreviewDTO> findScorePreview(String memberID);
}
