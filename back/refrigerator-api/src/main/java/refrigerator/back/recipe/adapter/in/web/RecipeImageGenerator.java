package refrigerator.back.recipe.adapter.in.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import refrigerator.back.global.image.ImageGenerator;

@Component
public class RecipeImageGenerator implements ImageGenerator {

    private final String route;
    private final String path;

    public RecipeImageGenerator(
            @Value("${s3.image.route}") String route,
            @Value("${s3.image.recipe.path}") String path) {
        this.route = route;
        this.path = path;
    }

    @Override
    public String getUrl(String imageName) {
        return route + path + imageName;
    }
}
