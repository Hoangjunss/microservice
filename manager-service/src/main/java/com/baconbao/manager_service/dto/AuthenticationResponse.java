package com.baconbao.manager_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class AuthenticationResponse {
    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private UserDTO user;
    private boolean isVaild;
    private  String role;
}