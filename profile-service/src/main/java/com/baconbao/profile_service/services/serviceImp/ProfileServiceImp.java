package com.baconbao.profile_service.services.serviceImp;

import com.baconbao.profile_service.dto.ContactDTO;
import com.baconbao.profile_service.dto.ProfileDTO;
import com.baconbao.profile_service.model.Contact;
import com.baconbao.profile_service.model.Profile;
import com.baconbao.profile_service.model.TypeProfile;
import com.baconbao.profile_service.repository.ProfileRepository;
import com.baconbao.profile_service.services.service.ProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
@Service
public class ProfileServiceImp implements ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProfileDTO saveProfile(ProfileDTO profileDTO) {
        return convertToDTO(save(profileDTO));
    }

    @Override
    public ProfileDTO updateProfile(ProfileDTO profileDTO) {

        Profile profile = convertToModel(profileDTO);
        profile.setContact(profileRepository.findById(profileDTO.getId()).orElseThrow().getContact());
        return convertToDTO(profileRepository.save(profile));
    }
    @Override
    public void updateContactByProfile(Contact contact,Integer id){
        Profile profile=profileRepository.findById(id).orElseThrow();
        profile.setContact(contact);
        profileRepository.save(profile);
    }


    public ProfileDTO convertToDTO(Profile profile) {
        return modelMapper.map(profile, ProfileDTO.class);
    }

    public Profile convertToModel(ProfileDTO profileDTO) {
        return modelMapper.map(profileDTO, Profile.class);
    }

    public Contact convertToContact(ContactDTO contactDTO) {
        return modelMapper.map(contactDTO, Contact.class);
    }

    private Profile save(ProfileDTO profileDTO) {
        Profile profile = Profile.builder()
                .objective(profileDTO.getObjective())
                .education(profileDTO.getEducation())
                .workExperience(profileDTO.getWorkExperience())
                .typeProfile(TypeProfile.valueOf(profileDTO.getTypeProfile()))
                .skills(profileDTO.getSkills())
                .id(getGenerationId())
                .build();
        return profileRepository.save(profile);
    }

    public Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        // Use most significant bits and ensure it's within the integer range
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}