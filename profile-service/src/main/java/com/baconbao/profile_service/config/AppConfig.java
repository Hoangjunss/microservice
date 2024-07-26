package com.baconbao.profile_service.config;

import com.baconbao.profile_service.dto.ContactDTO;
import com.baconbao.profile_service.dto.ProfileDTO;
import com.baconbao.profile_service.model.Contact;
import com.baconbao.profile_service.model.Profile;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {



    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Define mapping from ProfileDTO to Profile
        modelMapper.addMappings(new PropertyMap<ProfileDTO, Profile>() {
            @Override
            protected void configure() {
                map(source.getContactDTO(), destination.getContact());
                // Add other mappings if needed
            }
        });

        // Define mapping from Profile to ProfileDTO
        modelMapper.addMappings(new PropertyMap<Profile, ProfileDTO>() {
            @Override
            protected void configure() {
                map(source.getContact(), destination.getContactDTO());
                // Add other mappings if needed
            }
        });


        return modelMapper;
    }
}