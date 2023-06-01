package refrigerator.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.BackMainApplication;

@SpringBootTest
@Transactional
public class MainTest {

    public static void main(String[] args) {
        SpringApplication.run(BackMainApplication.class, args);
    }
}
