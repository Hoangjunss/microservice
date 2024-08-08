package com.example.profile_hr_service.services.service;

import java.util.List;

import com.example.profile_hr_service.dto.JobDTO;

public interface JopService {
    JobDTO findById(Integer id);
    JobDTO create(JobDTO job);
    JobDTO update(JobDTO job);
    List<JobDTO> getJobByIdCompany(Integer id);
    JobDTO applyJob(JobDTO jobDTO, Integer idProfile);
    JobDTO setIdProfileToJob(JobDTO jobDTO, Integer idProfile);
}