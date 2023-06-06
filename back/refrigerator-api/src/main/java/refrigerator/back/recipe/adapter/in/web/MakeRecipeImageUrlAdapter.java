package refrigerator.back.recipe.adapter.in.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import refrigerator.back.recipe.adapter.in.dto.InRecipeDTO;
import refrigerator.back.recipe.adapter.in.dto.InRecipeDetailDTO;
import refrigerator.back.recipe.adapter.in.dto.InRecipeRecommendDTO;

import java.util.List;

@Component
public class MakeRecipeImageUrlAdapter {

    private final String route;
    private final String path;

    public MakeRecipeImageUrlAdapter(
            @Value("${s3.image.route}") String route,
            @Value("${s3.image.recipe.path}") String path) {
        this.route = route;
        this.path = path;
    }

    public void toUrlByRecipeDetailDto(InRecipeDetailDTO data){
        data.settingImageUrl(createURL(data.getImage()));
    }

    public void toUrlByRecipeDto(List<InRecipeDTO> data){
        data.forEach(item -> item.settingImageUrl(createURL(item.getImage())));
    }

    public void toUrlByRecipeRecommendDto(List<InRecipeRecommendDTO> data){
        data.forEach(item -> item.settingImageUrl(createURL(item.getImage())));
    }

    private String createURL(String imageName){
        return route + path + imageName;
    }

}
