package com.baconbao.manager_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

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
    private List<Integer> idProfiePending;
    private List<Integer> idProfile;
    private Integer idCompany;
}
