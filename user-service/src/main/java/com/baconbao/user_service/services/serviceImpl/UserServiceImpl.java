package com.baconbao.user_service.services.serviceImpl;

import com.baconbao.user_service.dto.UserDTO;
import com.baconbao.user_service.exception.CustomException;
import com.baconbao.user_service.exception.Error;
import com.baconbao.user_service.utils.JwtTokenUtil;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
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
    public UserDTO findById(String token, Integer id) {
        log.info("Get user by id: {}", id);
        jwtTokenUtil.extractUsername(token);
        return convertToDto(userRepository.findById(id)
        .orElseThrow(() -> new CustomException(Error.USER_NOT_FOUND)));
    }

    @Override
    public boolean checkUser(Integer id) {
        return false;
    }

    @Override
    public UserDTO getCurrentUser(String token) {
        log.info("Get current user by token: {}", token);
        String email = jwtTokenUtil.extractUsername(token);
        return convertToDto(userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(Error.USER_NOT_FOUND)));
    }

    public UserDTO convertToDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    @Override
    public List<UserDTO> getALl(String token) {
        try {
            log.info("Get all");
            jwtTokenUtil.extractUsername(token);
            return convertToDTOList(userRepository.findAll());
        } catch (DataAccessException e) {
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public UserDTO update(String token, UserDTO userDTO) {
        try {
            log.info("Updating user by id: {}", userDTO.getId());
            jwtTokenUtil.extractUsername(token);
            return convertToDto(userRepository.save(convertToEntity(userDTO)));
        } catch (DataIntegrityViolationException e) {
            throw new CustomException(Error.USER_UNABLE_TO_UPDATE);
        } catch (DataAccessException e) {
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public UserDTO updateIsActive(String token, Integer id) {
        try {
            log.info("Updating active status by id: {}", id);
            UserDTO user = findById(token, id);
            user.setActive(!user.isActive());
            return convertToDto(userRepository.save(convertToEntity(user)));
        } catch (DataIntegrityViolationException e) {
            throw new CustomException(Error.USER_UNABLE_TO_UPDATE);
        } catch (DataAccessException e) {
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

}
