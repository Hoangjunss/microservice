package com.baconbao.manager_service.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

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
