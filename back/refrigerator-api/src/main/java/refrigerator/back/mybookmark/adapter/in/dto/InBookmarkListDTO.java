package refrigerator.back.mybookmark.adapter.in.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class InBookmarkListDTO {
    private List<InBookmarkDTO> bookmarks;
}
