package refrigerator.back.mybookmark.adapter.in.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class InBookmarkPreviewListDTO {
    private List<InBookmarkPreviewDTO> bookmarks;
    private Integer count;
}
