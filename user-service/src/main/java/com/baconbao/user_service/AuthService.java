package com.baconbao.user_service;


import com.baconbao.user_service.dto.AuthenticationRequest;
import com.baconbao.user_service.dto.AuthenticationResponse;
import com.baconbao.user_service.model.Role;
import com.baconbao.user_service.model.User;
import com.baconbao.user_service.repository.UserRepository;
import com.baconbao.user_service.security.OurUserDetailsService;
import com.baconbao.user_service.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.HashMap;
import java.util.UUID;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private OurUserDetailsService ourUserDetailsService;

    public AuthenticationResponse signUp(AuthenticationRequest registrationRequest){
        String rawPassword = registrationRequest.getPassword();
        log.info("Original password: " + rawPassword);

        String encodedPassword = passwordEncoder.encode(rawPassword);
        log.info("Encoded password: " + encodedPassword);

        log.info("Sign up Auth Service");

        User users = User.builder()
                .id(getGenerationId())
                .name(registrationRequest.getName())
                .email(registrationRequest.getEmail())
                .role(Role.valueOf(registrationRequest.getRole()))
                .build();

        users.setPassword(encodedPassword);
        log.info("User object before save: " + users);

        User userResult = userRepository.save(users);
        log.info("User object after save: " + userResult);

        log.info("pass new: " + users.getPassword());
        log.info("pass: " + registrationRequest.getPassword());

        return AuthenticationResponse.builder()
                .user(userResult)
                .message("User Saved Successfully")
                .statusCode(200)
                .build();
    }
    public AuthenticationResponse signIn(AuthenticationRequest signinRequest){

                log.info("Sign in Auth Service");
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword()));
                var user = userRepository.findByEmail(signinRequest.getEmail()).orElseThrow();
                System.out.println("USER IS: " + user);
                var jwt = jwtTokenUtil.generateToken(user);
                var refreshToken = jwtTokenUtil.generateRefreshToken(new HashMap<>(), user);
                return AuthenticationResponse.builder()
                        .statusCode(200)
                        .token(jwt)
                        .refreshToken(refreshToken)
                        .expirationTime("24Hr")
                        .message("Successfully Signed In")
                        .build();

    }

    public AuthenticationResponse refreshToken(AuthenticationRequest refreshTokenRequest){

            log.info("Refresh Token Auth Service");
            AuthenticationResponse.AuthenticationResponseBuilder response = AuthenticationResponse.builder();
            String ourEmail = jwtTokenUtil.extractUsername(refreshTokenRequest.getToken());
            User users = userRepository.findByEmail(ourEmail).orElseThrow();

                var jwt = jwtTokenUtil.generateToken(users);
                response.statusCode(200)
                        .token(jwt)
                        .refreshToken(refreshTokenRequest.getToken())
                        .expirationTime("24Hr")
                        .message("Successfully Refreshed Token");


            return response.build();

    }
    public AuthenticationResponse isValid(String token) {
       String email =jwtTokenUtil.extractUsername(token);
       if(email!=null){
           UserDetails userDetails=ourUserDetailsService.loadUserByUsername(email);
           if (jwtTokenUtil.isTokenValid(token,userDetails)){
               return AuthenticationResponse.builder().isVaild(true).build();
           }
       }
       return AuthenticationResponse.builder().isVaild(false).build()
;

    }

    public Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        // Use most significant bits and ensure it's within the integer range
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}
