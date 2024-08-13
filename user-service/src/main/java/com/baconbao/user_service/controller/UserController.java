package com.baconbao.user_service.controller;

import com.baconbao.user_service.AuthService;
import com.baconbao.user_service.dto.ApiResponse;
import com.baconbao.user_service.dto.AuthenticationRequest;
import com.baconbao.user_service.dto.AuthenticationResponse;
import com.baconbao.user_service.dto.UserDTO;
import com.baconbao.user_service.security.OurUserDetailsService;
import com.baconbao.user_service.services.service.UserService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
@Slf4j
public class UserController {
    @Autowired
    private AuthService authService;
    @Autowired
    private OurUserDetailsService ourUserDetailsService;
    @Autowired
    private UserService userService;
    @PostMapping("/isValid")
    public ApiResponse<AuthenticationResponse> isValid(@RequestBody String token) {
        ApiResponse<AuthenticationResponse> response = new ApiResponse<>(true, "", authService.isValid(token));
        log.info("log"+response.getData());
        return response;
    }
    @GetMapping("/ourUserDetailsService")
    public ResponseEntity<ApiResponse<UserDetails>> getUserDetails(@PathVariable String username) {
        UserDetails userDetails= ourUserDetailsService.loadUserByUsername(username);
        ApiResponse<UserDetails> response = new ApiResponse<>(true, "Get userdetails successfully", userDetails);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> signUp(@RequestBody AuthenticationRequest signUpRequest){
        AuthenticationResponse authenticationResponse = authService.signUp(signUpRequest);
        ApiResponse<AuthenticationResponse> response = new ApiResponse<>(true, "Sign up successfully", authenticationResponse);
        if (!authenticationResponse.isVaild()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
        return ResponseEntity.ok(response);
    }
    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> signIn(@RequestBody AuthenticationRequest signInRequest){
        AuthenticationResponse authenticationResponse = authService.signIn(signInRequest);
        ApiResponse<AuthenticationResponse> response = new ApiResponse<>(true, "Sign in successfully", authenticationResponse);
        if (!authenticationResponse.isVaild()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update")
    public ResponseEntity<ApiResponse<UserDTO>> update(@RequestParam String token,@RequestBody UserDTO userDTO){
        UserDTO updatedUser = userService.update(token, userDTO);
        ApiResponse<UserDTO> response = new ApiResponse<>(true, "User updated successfully", updatedUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/findbyid")
    public ResponseEntity<ApiResponse<UserDTO>> findById(@RequestParam String token,@RequestParam Integer id){
        ApiResponse<UserDTO> response = new ApiResponse<>(true, "User retrieved successfully", userService.findById(token, id));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> refreshToken(@RequestBody AuthenticationRequest refreshTokenRequest){
        ApiResponse<AuthenticationResponse> response = new ApiResponse<>(true, "Refresh token successfully", authService.refreshToken(refreshTokenRequest));
        return ResponseEntity.ok(response);
    }
    @GetMapping("/checkId")
    public ResponseEntity<ApiResponse<Boolean>> checkId(@RequestParam Integer id){
        ApiResponse<Boolean> response = new ApiResponse<>(true, "Check user id successfully", userService.checkUser(id));
        return ResponseEntity.ok(response);
    }
    @GetMapping("/getCurrentUser")
    public ResponseEntity<ApiResponse<UserDTO>> getCurrentUser(@RequestParam String token){
        ApiResponse<UserDTO> response = new ApiResponse<>(true, "Check user id successfully", userService.getCurrentUser(token));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUsers(@RequestParam String token){
        List<UserDTO> userDTOS = userService.getALl(token);
        ApiResponse<List<UserDTO>> response = new ApiResponse<>(true, "All users retrieved successfully", userDTOS);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/updateactive")
    public ResponseEntity<ApiResponse<UserDTO>> updateActive(@RequestParam String token,@RequestBody UserDTO userDTO){
        UserDTO updatedUser = userService.updateIsActive(token, userDTO.getId());
        ApiResponse<UserDTO> response = new ApiResponse<>(true, "User updated successfully", updatedUser);
        return ResponseEntity.ok(response); 
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<UserDTO>> deleteUser(@RequestParam String token,@RequestParam Integer id){
        UserDTO deletedUser = userService.deleteUser(token, id);
        ApiResponse<UserDTO> response = new ApiResponse<>(true, "User deleted successfully", deletedUser);
        return ResponseEntity.ok(response);
    }
}
