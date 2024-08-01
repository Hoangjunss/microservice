package com.baconbao.profile_service.services.service;

import com.baconbao.profile_service.dto.ProfileDTO;
import com.baconbao.profile_service.model.Profile;
import com.baconbao.profile_service.model.TypeProfile;

import java.util.List;

public interface ProfileService {
    ProfileDTO updateProfile(ProfileDTO profileDTO);
    ProfileDTO saveProfile(ProfileDTO profileDTO);
    ProfileDTO findById(Integer id);
    List<ProfileDTO> findProfilesByType(TypeProfile typeProfile);
    ProfileDTO convertToDTO(Profile profile);
    Profile convertToModel(ProfileDTO profileDTO);
    List<ProfileDTO> getAllProfile();
}
