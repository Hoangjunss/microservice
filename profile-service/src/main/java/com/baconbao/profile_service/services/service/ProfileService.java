package com.baconbao.profile_service.services.service;

import com.baconbao.profile_service.dto.ProfileDTO;
import com.baconbao.profile_service.model.Contact;

public interface ProfileService {
    ProfileDTO saveProfile(ProfileDTO profileDTO);
    ProfileDTO updateProfile(ProfileDTO profileDTO);
    void updateContactByProfile(Contact contact, Integer id);
}
