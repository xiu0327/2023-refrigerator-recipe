package refrigerator.back.global.common;

import org.springframework.stereotype.Component;
import refrigerator.back.comment.application.service.CommentTimeService;
import refrigerator.back.notification.application.domain.NotificationTimeService;


import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

@Component
public class TimeService implements CommentTimeService, NotificationTimeService {

    @Override
    public String replace(LocalDateTime date){
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(date, now);
        Period period = Period.between(date.toLocalDate(), now.toLocalDate());

        if(duration.toMinutes()<1){ return duration.toSeconds() + " 초 전";}
        if(duration.toHours()<1){ return duration.toMinutes() + " 분 전";}
        if(duration.toDays()<1){ return duration.toHours() + " 시간 전";}

        if(period.getMonths()<1){ return period.getDays() + " 일 전";}
        if (period.getYears()<1) { return period.getMonths() + " 달 전";}

        return period.getYears() + " 년 전";
    }
}
