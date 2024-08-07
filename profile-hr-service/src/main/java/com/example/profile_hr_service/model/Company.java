package com.example.profile_hr_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "company")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Company {
    @Id
    private Integer id;
    private String name;
    private String type;
    private String description;
    private String street;
    private String email;
    private String phone;
    private String city;
    private String country;
    private Integer idImage;
}
