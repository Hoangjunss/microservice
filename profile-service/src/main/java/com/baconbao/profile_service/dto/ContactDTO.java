package com.baconbao.profile_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactDTO {
    private Integer id;
    private String address;
    private String phone;
    private String email;
}
