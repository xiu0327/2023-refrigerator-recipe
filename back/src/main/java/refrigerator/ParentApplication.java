package refrigerator;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import refrigerator.integration.ServerApplication;

@SpringBootApplication
@ComponentScan(basePackageClasses = {ServerApplication.class, BackMainApplication.class})
public class ParentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParentApplication.class, args);
    }
}
