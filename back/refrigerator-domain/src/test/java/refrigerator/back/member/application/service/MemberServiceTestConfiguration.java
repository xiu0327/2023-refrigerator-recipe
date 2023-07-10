package refrigerator.back.member.application.service;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan(basePackages = {"refrigerator.back.member", "refrigerator.back.global"})
public class MemberServiceTestConfiguration {
}
