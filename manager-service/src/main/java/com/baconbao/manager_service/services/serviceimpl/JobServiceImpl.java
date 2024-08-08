package com.baconbao.manager_service.services.serviceimpl;

import com.baconbao.manager_service.dto.JobDTO;
import com.baconbao.manager_service.exception.CustomException;
import com.baconbao.manager_service.exception.Error;
import com.baconbao.manager_service.models.Job;
import com.baconbao.manager_service.models.TypeJob;
import com.baconbao.manager_service.repository.JobRepository;
import com.baconbao.manager_service.services.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class JobServiceImpl implements JobService {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MongoTemplate mongoTemplate;

    private Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0x7FFFFFFF);
    }

    private Job convertToModel(JobDTO jobDTO){
        return modelMapper.map(jobDTO, Job.class);
    }

    private JobDTO convertToDTO(Job job){
        return modelMapper.map(job, JobDTO.class);
    }

    private List<JobDTO> convertToListDTO(List<Job> jobs){
        return jobs.stream()
               .map(this::convertToDTO)
               .collect(java.util.stream.Collectors.toList());
    }

    private Job save(JobDTO jobDTO){
        try {
            log.info("Inserting job");
            Job job = Job.builder()
                    .id(getGenerationId())
                    .title(jobDTO.getTitle())
                    .description(jobDTO.getDescription())
                    .typeJob(TypeJob.valueOf(jobDTO.getTypeJob()))
                    .size(jobDTO.getSize())
                    .idProfiePending(jobDTO.getIdProfiePending())
                    .idProfile(jobDTO.getIdProfile())
                    .idCompany(jobDTO.getIdCompany())
                    .build();
            return jobRepository.save(job);
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }


    @Override
    public JobDTO findById(Integer id) {
        log.info("Finding job by id: {}", id);
        return convertToDTO(jobRepository.findById(id).orElseThrow(()-> new CustomException(Error.JOB_NOT_FOUND)));
    }

    @Override
    public JobDTO create(JobDTO job) {
        log.info("Creating job");
        return convertToDTO(save(job));
    }

    @Override
    public JobDTO update(JobDTO job) {
        try {
            return convertToDTO(jobRepository.save(convertToModel(job)));
        }  catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public List<JobDTO> getJobByCompany(Integer id) {
        try {
            log.info("Fetching jobs by company id: {}", id);
            Query query = new Query();
            query.addCriteria(Criteria.where("idCompany").is(id));
            return convertToListDTO(mongoTemplate.find(query, Job.class));
        } catch (DataAccessException e) {
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public JobDTO applyJob(JobDTO jobDTO, Integer idProfile) {
        if (jobDTO.getIdProfiePending() == null) {
            jobDTO.setIdProfiePending(new ArrayList<>());
        }
        jobDTO.getIdProfiePending().add(idProfile);
        return update(jobDTO);  
    }

    @Override
    public JobDTO acceptProfile(JobDTO jobDTO, Integer idProfile) {
        if (jobDTO.getIdProfiePending() != null) {
            jobDTO.getIdProfiePending().remove(idProfile);
        }
        if (jobDTO.getIdProfile() == null) {
            jobDTO.setIdProfile(new ArrayList<>());
        }
        jobDTO.getIdProfile().add(idProfile);
        return update(jobDTO);  
    }

    @Override
    public JobDTO rejectProfile(JobDTO jobDTO, Integer idProfile) {
        jobDTO.getIdProfiePending().remove(idProfile);
        return update(jobDTO);
    }


}
