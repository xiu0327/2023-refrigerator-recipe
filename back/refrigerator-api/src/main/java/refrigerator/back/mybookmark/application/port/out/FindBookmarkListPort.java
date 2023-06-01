package refrigerator.back.mybookmark.application.port.out;


import refrigerator.back.mybookmark.adapter.in.dto.InBookmarkDTO;

import java.util.List;

public interface FindBookmarkListPort {
    List<InBookmarkDTO> findBookmarkList(String memberId, int page, int size);
}
