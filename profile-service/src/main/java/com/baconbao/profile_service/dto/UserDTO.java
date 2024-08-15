package com.baconbao.profile_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDTO {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String idEmployee;
    private  String role;
    private boolean isActive;
}
