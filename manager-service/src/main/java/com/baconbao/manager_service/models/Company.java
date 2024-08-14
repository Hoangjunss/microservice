package com.baconbao.manager_service.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

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
    private String url;
    private Integer idManager;
    private List<Integer> idHr;
    private List<Integer> idJobs;
}