package refrigerator.server.api.recipe_recommend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.back.recipe_recommend.application.dto.RecommendRecipeDto;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecommendRecipeListResponseDto {

    List<RecommendRecipeDto> recipes;
}
