package refrigerator.back.myscore.adapter.in.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InMyScoreListDTO <T> {
    List<T> scores;
    Integer count;

    public InMyScoreListDTO(List<T> scores) {
        this.scores = scores;
    }

    public InMyScoreListDTO(List<T> scores, Integer count) {
        this.scores = scores;
        this.count = count;
    }
}