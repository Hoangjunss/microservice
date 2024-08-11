package com.baconbao.manager_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CompanyDTO {
    private Integer id;
    private String name;
    private String type;
    private String description;
    private String street;
    private String email;
    private String phone;
    private String city;
    private String country;
    private MultipartFile imageFile;
    private List<Integer> idHR;
}
