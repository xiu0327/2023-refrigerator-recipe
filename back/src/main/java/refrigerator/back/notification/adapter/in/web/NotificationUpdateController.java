package refrigerator.back.notification.adapter.in.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import refrigerator.back.notification.application.port.in.ChangeNotificationReadStatusUseCase;

@Controller
public class NotificationUpdateController {

    ChangeNotificationReadStatusUseCase modifyNotificationUseCase;


}
