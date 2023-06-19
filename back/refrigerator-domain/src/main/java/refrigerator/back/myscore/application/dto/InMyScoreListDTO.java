package refrigerator.back.myscore.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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