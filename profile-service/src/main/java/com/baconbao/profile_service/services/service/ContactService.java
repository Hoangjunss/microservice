package com.baconbao.profile_service.services.service;

import com.baconbao.profile_service.dto.ContactDTO;

public interface ContactService {
    ContactDTO saveContact(ContactDTO contactDTO);
    ContactDTO updateContact(ContactDTO contactDTO);
}
