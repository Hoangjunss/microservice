package com.baconbao.email_service;

import com.baconbao.email_service.dto.MailDTO;
import com.baconbao.email_service.dto.MessageDTO;
import com.baconbao.email_service.model.Mail;
import com.baconbao.email_service.openfeign.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UserClient userClient;

    @Override
    public void sendMail(Mail mail) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();// tao thu vien ho tro
        try {
            log.info("Sending mail to email: {}", mail.getMailFrom());
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(mail.getMailSubject()); // tiêu đề
            mimeMessageHelper.setFrom(new InternetAddress(mail.getMailFrom())); // ai gửi
            mimeMessageHelper.setTo(mail.getMailTo()); // gửi ai
            mimeMessageHelper.setText(mail.getMailContent()); // nội dung
            javaMailSender.send(mimeMessageHelper.getMimeMessage()); // thư viện hỗ trợ gửi mail
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Mail getMail(String mailTo, String content, String subject) {
        return Mail.builder()
                .mailTo(mailTo)
                .mailSubject(subject)
                .mailContent(content)
                .mailFrom("cvreviewbaconbao@gmail.com")
                .contentType("text/plain")
                .build();
    }

    @Override
    public void send(MessageDTO messageDTO) {
        log.info("send mail");
        log.info("send mail: {}", messageDTO.getId());
        MailDTO mailDTO = MailDTO.builder()
                .mailContent(messageDTO.getMessage())
                .mailSubject(messageDTO.getMessage())
                .mailTo(userClient.findById(messageDTO.getId()).getData().getEmail())

                .build();
        sendMail(getMail(mailDTO.getMailTo(), mailDTO.getMailContent(), mailDTO.getMailSubject()));
    }
}
