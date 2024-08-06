package com.baconbao.user_service.controller;

import com.baconbao.user_service.AuthService;
import com.baconbao.user_service.dto.AuthenticationRequest;
import com.baconbao.user_service.dto.AuthenticationResponse;
import com.baconbao.user_service.dto.UserDTO;
import com.baconbao.user_service.security.OurUserDetailsService;
import com.baconbao.user_service.services.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

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
    public AuthenticationResponse isValid(@RequestBody String token) {
        return authService.isValid(token);

    }
    @GetMapping("/ourUserDetailsService")
    public ResponseEntity<UserDetails> getUserDetails(@PathVariable String username) {
        UserDetails userDetails= ourUserDetailsService.loadUserByUsername(username);
        return ResponseEntity.ok(userDetails);
    }
    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> signUp(@RequestBody AuthenticationRequest signUpRequest){
        AuthenticationResponse response = authService.signUp(signUpRequest);
        if (!response.isVaild()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
        return ResponseEntity.ok(response);
    }
    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> signIn(@RequestBody AuthenticationRequest signInRequest){
        return ResponseEntity.ok(authService.signIn(signInRequest));
    }
    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody AuthenticationRequest refreshTokenRequest){
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }
    @GetMapping("/checkId")
    public ResponseEntity<Boolean> checkId(@RequestParam Integer id){
        return ResponseEntity.ok(userService.checkUser(id));
    }

}
