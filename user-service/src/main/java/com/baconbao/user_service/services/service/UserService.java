package com.baconbao.user_service.services.service;

import com.baconbao.user_service.dto.UserDTO;
import com.baconbao.user_service.model.User;
import org.springframework.stereotype.Service;


@Service
public interface UserService {
    User findById(Integer id);
    boolean checkUser(Integer id);
}

