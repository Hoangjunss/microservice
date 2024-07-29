package com.baconbao.profile_service.services.serviceImp;

import com.baconbao.profile_service.dto.ContactDTO;
import com.baconbao.profile_service.dto.ProfileDTO;
import com.baconbao.profile_service.exception.CustomException;
import com.baconbao.profile_service.exception.Error;
import com.baconbao.profile_service.model.Contact;
import com.baconbao.profile_service.model.Profile;
import com.baconbao.profile_service.model.TypeProfile;
import com.baconbao.profile_service.repository.ProfileRepository;
import com.baconbao.profile_service.services.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
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
        try{
            log.info("Saving profile");
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
        } catch (DataIntegrityViolationException e){
            throw new CustomException(Error.PROFILE_UNABLE_TO_SAVE);
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    public Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }

    @Override
    public ProfileDTO updateProfile(ProfileDTO profileDTO) {
        try{
            log.info("Updating profile id: {}", profileDTO.getId());
            return convertToDTO(profileRepository.save(convertToModel(profileDTO)));
        } catch (DataIntegrityViolationException e){
            throw new CustomException(Error.PROFILE_UNABLE_TO_UPDATE);
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public ProfileDTO saveProfile(ProfileDTO profileDTO) {
        log.debug("Save profile");
        Profile profile = save(profileDTO);
        //userService.updateProfileByUser(profile, profileDTO.getUserId());
        return convertToDTO(profile);
    }

    @Override
    public ProfileDTO findById(Integer id) {
        return convertToDTO(profileRepository.findById(id)
                .orElseThrow(()-> new CustomException(Error.PROFILE_NOT_FOUND)));
    }

    @Override
    public List<ProfileDTO> findProfilesByType(TypeProfile typeProfile) {
        try{
            return convertToDTOList(profileRepository.findByTypeProfile(typeProfile));
        } catch (InvalidDataAccessResourceUsageException e){
            log.error("Get profile by type failed: {}", e.getMessage());
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
        return null;
    }


    @Override
    public void updateContactByProfile(Contact contact, Integer id) {
        try{
            log.info("Update contact by profile id: {}", id);
            Profile profile=convertToModel(findById(id));
            profile.setContact(contact);
            profileRepository.save(profile);
        } catch (DataIntegrityViolationException e){
            throw new CustomException(Error.PROFILE_UNABLE_TO_UPDATE);
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public List<ProfileDTO> getAllProfile() {
        try{
            log.info("Get all profiles");
            return convertToDTOList(profileRepository.findAll());
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }
}
