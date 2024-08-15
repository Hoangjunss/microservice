package com.baconbao.email_service.controller;

import com.baconbao.email_service.MailService;
import com.baconbao.email_service.dto.ApiResponse;
import com.baconbao.email_service.dto.MailDTO;
import com.baconbao.email_service.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("email")
public class MailController {
    @Autowired
    private MailService mailService;
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<String>> send(@RequestBody MessageDTO messageDTO) {
        mailService.send(messageDTO);
        ApiResponse<String> response = new ApiResponse<>(true, "Check user id successfully","true"
           );
        return ResponseEntity.ok(response);
    }
}
