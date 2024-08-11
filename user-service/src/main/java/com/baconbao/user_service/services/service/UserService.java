package com.baconbao.user_service.services.service;

import com.baconbao.user_service.dto.UserDTO;
import com.baconbao.user_service.model.User;

import java.util.List;

import org.springframework.stereotype.Service;


@Service
public interface UserService {
    User findById(Integer id);
    boolean checkUser(Integer id);
    UserDTO getCurrentUser(String token);
    UserDTO update(String token, UserDTO userDTO);
    List<UserDTO> getALl(String token);
    UserDTO updateIsActive(String token,Integer id);
}

