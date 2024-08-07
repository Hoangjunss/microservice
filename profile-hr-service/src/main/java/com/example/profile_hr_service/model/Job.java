package com.example.profile_hr_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "job")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Job {
    @Id
    private  Integer id;
    private  String  description;
    private  TypeJob typeJob;
    private  Integer size;
    private Integer idProfie;
    private Integer idCompany;
}
