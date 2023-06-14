package refrigerator;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"refrigerator.server", "refrigerator.back"})
public class ParentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParentApplication.class, args);
    }
}
