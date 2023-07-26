package top.alexmmd.dog.service;

import javax.mail.MessagingException;

/**
 * @author wangyonghui
 * @since 2023年07月25日 14:50:00
 */
public interface IEmailService {

    void sendEmail(String toEmail, String subject, String body);

}
