package refrigerator.back.myscore.application.port.in;


import refrigerator.back.myscore.adapter.in.dto.response.InMyScoreListDTO;
import refrigerator.back.myscore.adapter.in.dto.response.InMyScorePreviewDTO;

public interface FindMyScorePreviewUseCase {
    InMyScoreListDTO<InMyScorePreviewDTO> findPreviewList(String memberID, int size);
}
