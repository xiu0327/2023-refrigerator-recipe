package refrigerator.server.api.ingredient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import refrigerator.back.global.image.ImageGenerator;

@Component
public class IngredientImageGenerator implements ImageGenerator {

    private final String route;
    private final String path;

    public IngredientImageGenerator(
            @Value("${s3.image.route}") String route,
            @Value("${s3.image.ingredient.path}") String path) {


        this.route = route;
        this.path = path;
    }

    @Override
    public String getUrl(String imageName) {
        return route + path + imageName;
    }

}
