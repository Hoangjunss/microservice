package com.baconbao.user_service.services.service;

import com.baconbao.user_service.dto.UserDTO;

import java.util.List;

import org.springframework.stereotype.Service;


@Service
public interface UserService {
    UserDTO findById(String token, Integer id);
    boolean checkUser(Integer id);
    UserDTO getCurrentUser();
    UserDTO update(String token, UserDTO userDTO);
    List<UserDTO> getALl(String token);
    List<UserDTO> findAllUserByIds(List<Integer> idUsers);
    UserDTO updateIsActive(String token,Integer id);
    UserDTO deleteUser(String token,Integer id);
    List<UserDTO> findUsersByIds(String token, List<Integer> idHR);
}

