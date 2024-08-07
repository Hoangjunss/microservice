package com.example.profile_hr_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

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
    private List<Integer> idProfiePending;
    private List<Integer> idProfile;
    private Integer idCompany;
}
