package com.baconbao.user_service.services.service;

import com.baconbao.user_service.dto.UserDTO;
import org.springframework.stereotype.Service;


@Service
public interface UserService {
    UserDTO findById(Integer id);
}

