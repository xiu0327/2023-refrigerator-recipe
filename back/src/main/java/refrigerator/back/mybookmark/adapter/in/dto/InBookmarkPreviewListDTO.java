package refrigerator.back.mybookmark.adapter.in.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class InBookmarkPreviewListDTO {
    private List<InBookmarkPreviewDTO> bookmarks;
    private Integer count;
}
