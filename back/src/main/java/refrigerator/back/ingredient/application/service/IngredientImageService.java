package refrigerator.back.ingredient.application.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import refrigerator.back.ingredient.application.port.in.MakeImageUrlUseCase;

@Service
public class IngredientImageService implements MakeImageUrlUseCase {
    @Value("${s3.image.route}")
    private String route;
    @Value("${s3.image.profile.path}")
    private String path;

    @Override
    public String createURL(String imageName) {
        return route + path + imageName;
    }

}


