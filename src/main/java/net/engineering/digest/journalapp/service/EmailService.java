package net.engineering.digest.journalapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;


    public void sendEmail(String to, String subject, String text) {
        log.info("Sending email to {}", to);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("from_mail");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

}
