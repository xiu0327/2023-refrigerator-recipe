package refrigerator.back.myscore.adapter.out.repository;

import org.springframework.data.domain.Pageable;
import refrigerator.back.myscore.adapter.out.dto.MyRecipeScoreMappingDTO;

import java.util.List;

public interface MyRecipeScoreQueryRepository {
    MyRecipeScoreMappingDTO findMyRecipeScoreById(Long scoreID);
    List<MyRecipeScoreMappingDTO> findMyRecipeScoreList(String memberID, Pageable pageable);
}
