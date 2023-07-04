package refrigerator.back.global.common;

import org.springframework.stereotype.Component;
import refrigerator.back.comment.application.service.CommentTimeService;
import refrigerator.back.notification.application.domain.NotificationTimeService;


import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

@Component
public class TimeService implements CommentTimeService, NotificationTimeService {

    // TODO: replce 내부 LocalDateTime.now() 가 있어서 밖에서 주입 받는 걸로 바꾸고 Service 영역에서 받는걸로 바꿈 
    // TODO : 로직 수정 period에 문제가 있음. period는 duration 처럼 걸러주는 게 아님 // period -> chronoUnit으로 변경
    
    @Override
    public String replace(LocalDateTime date, LocalDateTime now){
        Duration duration = Duration.between(date, now);

        long days = ChronoUnit.DAYS.between(date.toLocalDate(), now.toLocalDate());
        long months = ChronoUnit.MONTHS.between(date.toLocalDate(), now.toLocalDate());
        long years = ChronoUnit.YEARS.between(date.toLocalDate(), now.toLocalDate());

        if(duration.toMinutes()<1){ return duration.toSeconds() + " 초 전";}
        if(duration.toHours()<1){ return duration.toMinutes() + " 분 전";}
        if(duration.toDays()<1){ return duration.toHours() + " 시간 전";}

        if(months<1){ return days + " 일 전";}
        if(years<1) { return months + " 달 전";}

        return years + " 년 전";
    }
}
