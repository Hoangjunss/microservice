package com.baconbao.email_service.model;

import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data

@Builder
public class Mail
{
    private String mailFrom;
    private String mailTo;
    private String mailCc;
    private String mailBcc;


    private String mailSubject;
    private String mailContent;
    private String contentType;
    private List <Object> attachments;

    public Date getMailSendDate() {
        return new Date();
    }
}
