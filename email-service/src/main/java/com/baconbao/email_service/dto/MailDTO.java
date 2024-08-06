package com.baconbao.email_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MailDTO {
    private String mailTo;
    private String mailSubject;
    private String mailContent;
}
