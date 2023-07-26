package top.alexmmd.dog.service.impl;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.alexmmd.dog.service.IEmailService;

/**
 * @author wangyonghui
 * @since 2023年07月25日 14:50:00
 */
@Slf4j
@Service
public class EmailServiceImpl implements IEmailService {

    @Value("${email.password}")
    private String password;

    public void sendEmail(String toEmail, String subject, String body) {
        String fromEmail = "2930807240@qq.com";
        String smtpHost = "smtp.qq.com";
        int smtpPort = 587;
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
        } catch (MessagingException e) {
            log.error("发送邮件出错", e);
        }
    }

    public static void main(String[] args) {

        // Create the EmailService instance
        EmailServiceImpl emailService = new EmailServiceImpl();

        // Send an email
        String toEmail = "201520180114@ecut.edu.cn";
        String subject = "Test Email";
        String body = "This is a test email sent using JavaMail API.";

        emailService.sendEmail(toEmail, subject, body);
        System.out.println("Email sent successfully.");
    }
}
