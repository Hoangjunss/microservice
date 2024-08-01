package com.baconbao.profile_service.model;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    @Id
    private Integer id;
    private String address;
    private String phone;
    private String email;
}
