package refrigerator.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import refrigerator.BackMainApplication;

@SpringBootApplication
@ComponentScan(basePackageClasses = BackMainApplication.class)
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
