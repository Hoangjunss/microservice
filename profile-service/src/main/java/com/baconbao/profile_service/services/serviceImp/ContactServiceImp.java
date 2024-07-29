package com.baconbao.profile_service.services.serviceImp;

import com.baconbao.profile_service.dto.ContactDTO;
import com.baconbao.profile_service.dto.ProfileDTO;
import com.baconbao.profile_service.exception.CustomException;
import com.baconbao.profile_service.exception.Error;
import com.baconbao.profile_service.model.Contact;
import com.baconbao.profile_service.model.Profile;
import com.baconbao.profile_service.repository.ContactRepository;
import com.baconbao.profile_service.services.service.ContactService;
import com.baconbao.profile_service.services.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@Slf4j
public class ContactServiceImp implements ContactService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ProfileService profileService;

    private Contact save(ContactDTO contactDTO){
        try {
            log.info("Saving contact");
            Contact contact=Contact.builder()
                    .id(getGenerationId())
                    .email(contactDTO.getEmail())
                    .phone(contactDTO.getPhone())
                    .address(contactDTO.getAddress())
                    .build();
            return contactRepository.save(contact);
        } catch (DataIntegrityViolationException e){
            throw new CustomException(Error.CONTACT_UNABLE_TO_SAVE);
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }
    public Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
    public ContactDTO convertToDTO(Contact contact) {
        return modelMapper.map(contact, ContactDTO.class);
    }

    public Contact convertToModel(ContactDTO contactDTO) {
        return modelMapper.map(contactDTO, Contact.class);
    }


    @Override
    public ContactDTO saveContact(ContactDTO contactDTO) {
        log.info("Save contact");
        Contact contact = save(contactDTO);
        profileService.updateContactByProfile(contact,contactDTO.getProfileID());
        return convertToDTO(contact);
    }

    @Override
    public ContactDTO updateContact(ContactDTO contactDTO) {
        try{
            return convertToDTO(contactRepository.save(convertToModel(contactDTO)));
        } catch (DataIntegrityViolationException e){
            throw new CustomException(Error.CONTACT_UNABLE_TO_UPDATE);
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public ContactDTO getContactByProfile(Integer id) {
        try{
            Profile profile=profileService.convertToModel(profileService.findById(id));
            return null;
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }
}
