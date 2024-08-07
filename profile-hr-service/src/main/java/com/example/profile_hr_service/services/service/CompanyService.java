package com.example.profile_hr_service.services.service;

import com.example.profile_hr_service.dto.CompanyDTO;
import com.example.profile_hr_service.dto.JobDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompanyService {
    CompanyDTO findById(Integer id);
    CompanyDTO create(CompanyDTO company);
    CompanyDTO update(CompanyDTO company);
    void deleteById(Integer id);
    List<CompanyDTO> getCompanyDTOs();
    List<CompanyDTO> getCompanyByType(String type);
    List<JobDTO> getJobByCompany(Integer id);
    JobDTO setIdProfileToJob(JobDTO jobDTO,Integer idProfile);
}
