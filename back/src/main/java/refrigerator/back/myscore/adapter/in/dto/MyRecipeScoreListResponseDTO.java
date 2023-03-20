package refrigerator.back.myscore.adapter.in.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MyRecipeScoreListResponseDTO {
    private List<MyRecipeScoreDTO> scores;
    private int count;
}
