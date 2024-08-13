package com.baconbao.user_service.services.service;

import com.baconbao.user_service.dto.UserDTO;

import java.util.List;

import org.springframework.stereotype.Service;


@Service
public interface UserService {
    UserDTO findById(String token, Integer id);
    boolean checkUser(Integer id);
    UserDTO getCurrentUser(String token);
    UserDTO update(String token, UserDTO userDTO);
    List<UserDTO> getALl(String token);
    UserDTO updateIsActive(String token,Integer id);
    UserDTO deleteUser(String token,Integer id);
}

