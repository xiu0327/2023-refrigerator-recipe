package refrigerator.back;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.UUID;

@SpringBootTest
public class EmailSenderTest {

    @Autowired JavaMailSender javaMailSender;

    @Test
    void 테스트() throws MessagingException {
        String code = UUID.randomUUID().toString();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        mimeMessage.addRecipients(MimeMessage.RecipientType.TO, "codinging0326@gmail.com");
        mimeMessage.setSubject("[인증코드] " + code);
        mimeMessage.setText(code, "utf-8", "html");
        javaMailSender.send(mimeMessage);
    }
}
