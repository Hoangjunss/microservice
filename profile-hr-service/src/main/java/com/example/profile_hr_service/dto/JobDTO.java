package com.example.profile_hr_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class JobDTO {
    @Id
    private  Integer id;
    private  String  description;
    private String typeJob;
    private  Integer size;
}
