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

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProfileServiceImp implements ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ModelMapper modelMapper;

    public Contact convertToContact(ContactDTO contactDTO) {
        return modelMapper.map(contactDTO, Contact.class);
    }

    public List<ProfileDTO> convertToDTOList(List<Profile> profiles) {
        return profiles.stream()
                .map(project -> modelMapper.map(project, ProfileDTO.class))
                .collect(Collectors.toList());
    }

    public ProfileDTO convertToDTO(Profile profile) {
        return modelMapper.map(profile, ProfileDTO.class);
    }

    public Profile convertToModel(ProfileDTO profileDTO) {
        return modelMapper.map(profileDTO, Profile.class);
    }

    private Profile save(ProfileDTO profileDTO) {
        Profile profile = Profile.builder()
                .objective(profileDTO.getObjective())
                .education(profileDTO.getEducation())
                .workExperience(profileDTO.getWorkExperience())
                .typeProfile(TypeProfile.valueOf(profileDTO.getTypeProfile()))
                .skills(profileDTO.getSkills())
                //.image()
                .id(getGenerationId())
                .build();
        return profileRepository.save(profile);
    }

    public Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }

    @Override
    public ProfileDTO updateProfile(ProfileDTO profileDTO) {
        return convertToDTO(profileRepository.save(convertToModel(profileDTO)));

    }

    @Override
    public ProfileDTO saveProfile(ProfileDTO profileDTO) {
        Profile profile = save(profileDTO);
        //userService.updateProfileByUser(profile, profileDTO.getUserId());
        return convertToDTO(profile);
    }

    @Override
    public ProfileDTO findById(Integer id) {
        return convertToDTO(profileRepository.findById(id)
                .orElseThrow());
    }

    @Override
    public List<ProfileDTO> findProfilesByType(TypeProfile typeProfile) {
        return convertToDTOList(profileRepository.findByTypeProfile(typeProfile));
    }


    @Override
    public void updateContactByProfile(Contact contact, Integer id) {
        Profile profile=profileRepository.findById(id).orElseThrow();
        profile.setContact(contact);
        profileRepository.save(profile);
    }

    @Override
    public List<ProfileDTO> getAllProfile() {
        return convertToDTOList(profileRepository.findAll());
    }
}
