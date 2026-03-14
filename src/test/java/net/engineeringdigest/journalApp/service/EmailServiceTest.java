package net.engineeringdigest.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService mailSender;

    @Test
    public void testSendEmail() {
        mailSender.sendEmail("abhinavtrivedii@gmail.com"
        , "Subject testing email from journal app"
    , "This is a test email sent from the Journal App's EmailService.");
    }
}
