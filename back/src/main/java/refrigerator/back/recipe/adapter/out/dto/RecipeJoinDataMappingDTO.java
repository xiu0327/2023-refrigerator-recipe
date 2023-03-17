package refrigerator.back.recipe.adapter.out.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecipeJoinDataMappingDTO {
    private Integer score;
    private Integer person;
    private Integer views;
    private Integer bookmarks;
    private String recipeFoodTypeName;
    private String recipeCategoryName;

    @QueryProjection
    public RecipeJoinDataMappingDTO(Integer score, Integer person, Integer views, Integer bookmarks, String recipeFoodTypeName, String recipeCategoryName) {
        this.score = score;
        this.person = person;
        this.views = views;
        this.bookmarks = bookmarks;
        this.recipeFoodTypeName = recipeFoodTypeName;
        this.recipeCategoryName = recipeCategoryName;
    }
}
