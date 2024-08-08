package com.baconbao.manager_service.services.service;

import com.baconbao.manager_service.dto.JobDTO;

import java.util.List;

public interface JobService {
    JobDTO findById(Integer id);
    JobDTO create(JobDTO job);
    JobDTO update(JobDTO job);
    List<JobDTO> getJobByCompany(Integer id);
    JobDTO applyJob(JobDTO jobDTO,Integer idProfile);
    JobDTO acceptProfile(JobDTO jobDTO,Integer idProfile);
    JobDTO rejectProfile(JobDTO jobDTO,Integer idProfile);
}
