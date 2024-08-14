package com.baconbao.profile_service.services.service;

import com.baconbao.profile_service.dto.ProfileDTO;
import com.baconbao.profile_service.model.Profile;
import com.baconbao.profile_service.model.TypeProfile;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface ProfileService {
    ProfileDTO updateProfile(ProfileDTO profileDTO,MultipartFile imageFile);
    ProfileDTO saveProfile(ProfileDTO profileDTO);
    ProfileDTO findById(Integer id);
    ProfileDTO findByIdUser(Integer id);
    List<ProfileDTO> findProfilesByType(TypeProfile typeProfile);
    ProfileDTO convertToDTO(Profile profile);
    Profile convertToModel(ProfileDTO profileDTO);
    List<ProfileDTO> getAllProfile();
    List<ProfileDTO> findByTitle(String title);
    List<ProfileDTO> findListProfileByIdPendingJob(List<Integer> idJobs);
    Boolean checkIdProfile(Integer id);
    
}