package refrigerator.back.myscore.adapter.out.repository;

import org.springframework.data.domain.Pageable;
import refrigerator.back.myscore.adapter.out.dto.MyRecipeScoreMappingDTO;
import refrigerator.back.myscore.adapter.out.dto.MyRecipeScorePreviewMappingDTO;

import java.util.List;

public interface MyRecipeScoreQueryRepository {
    List<MyRecipeScoreMappingDTO> findMyRecipeScoreList(String memberID, Pageable pageable);
    List<MyRecipeScorePreviewMappingDTO> findScorePreview(String memberID);
    Integer findMyRecipeScoreCount(String memberID);
}
