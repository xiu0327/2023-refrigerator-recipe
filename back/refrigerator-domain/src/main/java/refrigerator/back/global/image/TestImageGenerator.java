package refrigerator.back.global.image;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@RequiredArgsConstructor
public class TestImageGenerator implements ImageGenerator {

    private final String route;
    private final String path;

    @Override
    public String getUrl(String imageName) {
        return route + path + imageName;
    }
}
