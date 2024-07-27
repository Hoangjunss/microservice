package com.baconbao.profile_service.controller;

import com.baconbao.profile_service.dto.ContactDTO;
import com.baconbao.profile_service.dto.ProfileDTO;
import com.baconbao.profile_service.services.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contact")
public class ContactController {
    @Autowired
    private ContactService contactService;
    @PostMapping("/save")
    public ResponseEntity<ContactDTO> save(@RequestBody ContactDTO contactDTO){
        return ResponseEntity.ok(contactService.saveContact(contactDTO));
    }
    @PostMapping("/update")
    public ResponseEntity<ContactDTO> update(@RequestBody ContactDTO contactDTO){
        return ResponseEntity.ok(contactService.updateContact(contactDTO));
    }
}
