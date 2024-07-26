package com.baconbao.profile_service.services.serviceImp;

import com.baconbao.profile_service.dto.ContactDTO;
import com.baconbao.profile_service.dto.ProfileDTO;
import com.baconbao.profile_service.model.Contact;
import com.baconbao.profile_service.model.Profile;
import com.baconbao.profile_service.repository.ProfileRepository;
import com.baconbao.profile_service.services.service.ProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

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
        Profile profile = profileRepository.findById(profileDTO.getId()).orElseThrow();

        // Tạo hoặc cập nhật contact
        Contact contact = profileDTO.getContactDTO() != null
                ? Contact.builder()
                .address(profileDTO.getContactDTO().getAddress())
                .phone(profileDTO.getContactDTO().getPhone())
                .email(profileDTO.getContactDTO().getEmail())
                .build()
                : profile.getContact();

        return convertToDTO(profileRepository.save(profile));
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
                .skills(profileDTO.getSkills())
                .id(getGenerationId())
                .contact(convertToContact(profileDTO.getContactDTO()))
                .build();
        return profileRepository.save(profile);
    }

    public Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        // Use most significant bits and ensure it's within the integer range
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}
