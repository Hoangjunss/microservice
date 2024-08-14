package com.baconbao.manager_service.services.service;

import com.baconbao.manager_service.dto.JobDTO;

import java.util.List;

public interface JobService {
    JobDTO findById(Integer id);
    JobDTO create(JobDTO job);
    JobDTO update(JobDTO job);
    JobDTO delete(Integer id);
    List<JobDTO> getAllJobs();
    List<JobDTO> getJobByCompany(Integer id);
    List<JobDTO> getNewJob(Integer id);
    List<JobDTO> getJobByPrfilePending(Integer id);
    List<JobDTO> getJobByProfileAccepted(Integer id);
    JobDTO applyJob(Integer idJob,Integer idProfile);
    JobDTO acceptProfile(Integer idJob,Integer idProfile);
    JobDTO rejectProfile(Integer idJob,Integer idProfile);
}
