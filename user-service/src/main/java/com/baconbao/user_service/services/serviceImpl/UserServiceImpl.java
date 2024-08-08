package com.baconbao.user_service.services.serviceImpl;


import com.baconbao.user_service.AuthService;
import com.baconbao.user_service.dto.UserDTO;
import com.baconbao.user_service.utils.JwtTokenUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baconbao.user_service.model.User;
import com.baconbao.user_service.repository.UserRepository;
import com.baconbao.user_service.services.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public boolean checkUser(Integer id) {
        return false;
    }

    @Override
    public UserDTO getCurrentUser(String token) {
        String email=jwtTokenUtil.extractUsername(token);

        return convertToDto(userRepository.findByEmail(email).orElseThrow());
    }
    public UserDTO convertToDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

}

