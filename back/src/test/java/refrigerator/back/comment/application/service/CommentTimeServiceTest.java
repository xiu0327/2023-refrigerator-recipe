package refrigerator.back.comment.application.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentTimeServiceTest {

    @Autowired CommentTimeService commentTimeService;

    @Test
    void replace() {
        LocalDateTime time = LocalDateTime.of(2023, 5, 12, 19, 2);
        String result = commentTimeService.replace(time);
//        Assertions.assertThat(result).isEqualTo("4 분 전");
        System.out.println("CommentTimeServiceTest.replace");
    }
}