package com.baconbao.user_service.controller;

import com.baconbao.user_service.AuthService;
import com.baconbao.user_service.dto.ApiResponse;
import com.baconbao.user_service.dto.AuthenticationRequest;
import com.baconbao.user_service.dto.AuthenticationResponse;
import com.baconbao.user_service.security.OurUserDetailsService;
import com.baconbao.user_service.services.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
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

}
