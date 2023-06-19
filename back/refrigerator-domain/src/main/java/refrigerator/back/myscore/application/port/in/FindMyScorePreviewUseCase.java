package refrigerator.back.myscore.application.port.in;


import refrigerator.back.myscore.application.dto.InMyScoreListDTO;
import refrigerator.back.myscore.application.dto.InMyScorePreviewDTO;

public interface FindMyScorePreviewUseCase {
    InMyScoreListDTO<InMyScorePreviewDTO> findPreviewList(String memberID, int size);
}
