package com.baconbao.user_service.config;

import com.baconbao.user_service.dto.UserDTO;
import com.baconbao.user_service.model.Role;
import com.baconbao.user_service.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Custom mapping for User to UserDTO
        modelMapper.typeMap(User.class, UserDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getRole().name(), UserDTO::setRole);
        });

        // Custom mapping for UserDTO to User
        modelMapper.typeMap(UserDTO.class, User.class).addMappings(mapper -> {
            mapper.map(src -> Role.valueOf(src.getRole()), User::setRole);
        });

        return modelMapper;
    }
}
