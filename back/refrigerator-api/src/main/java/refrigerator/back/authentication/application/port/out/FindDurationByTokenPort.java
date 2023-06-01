package refrigerator.back.authentication.application.port.out;

import java.util.Date;

public interface FindDurationByTokenPort {
    Date findDuration(String token);
}
