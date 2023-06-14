package refrigerator.integration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import refrigerator.BackMainApplication;

@TestConfiguration
@ComponentScan(basePackageClasses = BackMainApplication.class)
public class IntegrationTestConfiguration {
}
