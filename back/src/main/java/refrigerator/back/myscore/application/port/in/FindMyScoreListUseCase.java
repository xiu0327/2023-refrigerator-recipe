package refrigerator.back.myscore.application.port.in;

import refrigerator.back.myscore.adapter.in.dto.response.InMyScoreDTO;
import refrigerator.back.myscore.adapter.in.dto.response.InMyScoreListDTO;

public interface FindMyScoreListUseCase {
    InMyScoreListDTO<InMyScoreDTO> findMyScoreList(String memberID, int page, int size);
}
