package com.baconbao.email_service;

import com.baconbao.email_service.model.Mail;
import org.springframework.stereotype.Service;


@Service
public interface MailService {
    void sendMail(Mail mail);
    Mail getMail(String mailTo,String content,String subject);
}
