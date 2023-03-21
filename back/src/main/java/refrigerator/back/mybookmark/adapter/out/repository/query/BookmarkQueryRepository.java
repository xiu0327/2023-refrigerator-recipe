package refrigerator.back.mybookmark.adapter.out.repository.query;

import org.springframework.data.domain.Pageable;
import refrigerator.back.mybookmark.adapter.out.dto.OutBookmarkDTO;
import refrigerator.back.mybookmark.adapter.out.dto.OutBookmarkPreviewDTO;

import java.util.List;

public interface BookmarkQueryRepository {
    List<OutBookmarkPreviewDTO> findBookmarkPreview(String memberId);
    List<OutBookmarkDTO> findBookmarkList(String memberId, Pageable pageable);
}
