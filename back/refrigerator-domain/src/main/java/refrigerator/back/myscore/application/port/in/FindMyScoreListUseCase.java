package refrigerator.back.myscore.application.port.in;

import refrigerator.back.myscore.application.dto.InMyScoreDTO;
import refrigerator.back.myscore.application.dto.InMyScoreListDTO;

public interface FindMyScoreListUseCase {
    InMyScoreListDTO<InMyScoreDTO> findMyScoreList(String memberID, int page, int size);
}
