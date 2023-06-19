package refrigerator.back.mybookmark.application.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class InBookmarkPreviewListDTO {
    private List<InBookmarkPreviewDTO> bookmarks;
    private Integer count;
}
