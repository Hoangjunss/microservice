package com.baconbao.user_service.services.serviceImpl;

import com.baconbao.user_service.dto.UserDTO;
import com.baconbao.user_service.utils.JwtTokenUtil;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baconbao.user_service.model.User;
import com.baconbao.user_service.repository.UserRepository;
import com.baconbao.user_service.services.service.UserService;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private ModelMapper modelMapper;

    public List<UserDTO> convertToDTOList(List<User> users) {
        return users.stream()
                .map(project -> modelMapper.map(project, UserDTO.class))
                .collect(Collectors.toList());
    }

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
        String email = jwtTokenUtil.extractUsername(token);

        return convertToDto(userRepository.findByEmail(email).orElseThrow());
    }

    public UserDTO convertToDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    @Override
    public List<UserDTO> getALl(String token) {
        log.info("Get all");
        jwtTokenUtil.extractUsername(token);
        return convertToDTOList(userRepository.findAll());
    }

    @Override
    public UserDTO update(String token, UserDTO userDTO) {
        jwtTokenUtil.extractUsername(token);
        return convertToDto(userRepository.save(convertToEntity(userDTO)));
    }

    @Override
    public UserDTO updateIsActive(String token,Integer id) {
        User user = findById(id);
        user.setActive(!user.isActive());
        return convertToDto(userRepository.save(user));
    }
    

}
