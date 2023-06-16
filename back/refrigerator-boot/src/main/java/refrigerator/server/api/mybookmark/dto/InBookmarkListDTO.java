package refrigerator.server.api.mybookmark.dto;

import lombok.Builder;
import lombok.Getter;
import refrigerator.back.mybookmark.application.dto.InBookmarkDTO;

import java.util.List;

@Getter
@Builder
public class InBookmarkListDTO {
    private List<InBookmarkDTO> bookmarks;
}
