package com.baconbao.profile_service.config;

import com.baconbao.profile_service.dto.ProfileDTO;
import com.baconbao.profile_service.model.Contact;
import com.baconbao.profile_service.model.Profile;
import com.baconbao.profile_service.model.TypeProfile;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {



    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Converter from String to TypeProfile
        Converter<String, TypeProfile> toTypeProfile = new Converter<>() {
            @Override
            public TypeProfile convert(MappingContext<String, TypeProfile> context) {
                String source = context.getSource();
                if (source == null || source.trim().isEmpty()) {
                    return null; // or handle this case as per your requirement
                }
                try {
                    return TypeProfile.valueOf(source);
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("Invalid value for TypeProfile enum: " + source);
                }
            }
        };

        // Define mapping from ProfileDTO to Profile
        modelMapper.addMappings(new PropertyMap<ProfileDTO, Profile>() {
            @Override
            protected void configure() {
                using(toTypeProfile).map(source.getTypeProfile()).setTypeProfile(null);
            }
        });

        // Converter from TypeProfile to String
        Converter<TypeProfile, String> toString = new Converter<>() {
            @Override
            public String convert(MappingContext<TypeProfile, String> context) {
                TypeProfile source = context.getSource();
                return source == null ? null : source.name();
            }
        };

        // Define mapping from Profile to ProfileDTO
        modelMapper.addMappings(new PropertyMap<Profile, ProfileDTO>() {
            @Override
            protected void configure() {
                using(toString).map(source.getTypeProfile()).setTypeProfile(null);
            }
        });

        return modelMapper;
    }
}