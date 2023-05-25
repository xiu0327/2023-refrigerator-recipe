package refrigerator.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MainTest {

    public static void main(String[] args) {
        SpringApplication.run(BackApplication.class, args);
    }
}
