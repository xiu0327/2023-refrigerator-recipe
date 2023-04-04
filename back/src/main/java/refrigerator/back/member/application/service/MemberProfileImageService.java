package refrigerator.back.member.application.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import refrigerator.back.member.application.port.in.CreateImageUrlUseCase;

@Service
public class MemberProfileImageService implements CreateImageUrlUseCase {

    @Value("${s3.image.route}")
    private String route;
    @Value("${s3.image.profile.path}")
    private String path;

    @Override
    public String createURL(String imageName) {
        return route + path + imageName;
    }
}
