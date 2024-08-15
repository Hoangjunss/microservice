package com.baconbao.manager_service.services.serviceimpl;

import com.baconbao.manager_service.dto.JobDTO;
import com.baconbao.manager_service.dto.ProfileDTO;
import com.baconbao.manager_service.exception.CustomException;
import com.baconbao.manager_service.exception.Error;
import com.baconbao.manager_service.models.Job;
import com.baconbao.manager_service.models.TypeJob;
import com.baconbao.manager_service.openfeign.EmailClient;
import com.baconbao.manager_service.openfeign.MessageDTO;
import com.baconbao.manager_service.openfeign.NotificationClient;
import com.baconbao.manager_service.openfeign.ProfileClient;
import com.baconbao.manager_service.repository.JobRepository;
import com.baconbao.manager_service.services.service.JobService;
import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoCommandException;
import com.mongodb.MongoWriteException;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
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
    @Autowired
    private ProfileClient profileClient;
    @Autowired
    private NotificationClient notificationClient;
    @Autowired
    private EmailClient emailClient;


    private Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0x7FFFFFFF);
    }

    private Job convertToModel(JobDTO jobDTO) {
        return modelMapper.map(jobDTO, Job.class);
    }

    private JobDTO convertToDTO(Job job) {
        return modelMapper.map(job, JobDTO.class);
    }

    private List<JobDTO> convertToListDTO(List<Job> jobs) {
        return jobs.stream()
                .map(this::convertToDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    private Job save(JobDTO jobDTO) {
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
            return jobRepository.insert(job);
        } catch (DuplicateKeyException e) {
            throw new CustomException(Error.MONGO_DUPLICATE_KEY_ERROR);
        } catch (DataAccessException e) {
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public JobDTO findById(Integer id) {
        log.info("Finding job by id: {}", id);
        return convertToDTO(jobRepository.findById(id)
                .orElseThrow(() -> new CustomException(Error.JOB_NOT_FOUND)));
    }

    @Override
    public JobDTO create(JobDTO job) {
        log.info("Creating job");
        return convertToDTO(save(job));
    }

    @Override
    public JobDTO update(JobDTO job) {
        try {
            log.info("Updating job by id: {}", job.getId());
            return convertToDTO(jobRepository.save(convertToModel(job)));
        } catch (MongoWriteException e) {
            throw new CustomException(Error.MONGO_WRITE_CONCERN_ERROR);
        } catch (DataAccessException e) {
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public List<JobDTO> getAllJobs() {
        try {
            log.info("Fetching all jobs");
            Query query = new Query();
            query.limit(20);
            return convertToListDTO(mongoTemplate.find(query, Job.class));
        } catch (DataAccessException e) {
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
        } catch (MongoCommandException e) {
            throw new CustomException(Error.MONGO_QUERY_EXECUTION_ERROR);
        } catch (DataAccessException e) {
            System.err.println("Error while fetching companies by type: " + e.getMessage());
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public JobDTO applyJob(Integer idJob, Integer idProfile) {
        try {
            log.info("Applying for job id: {}, profile id: {}", idJob, idProfile);

            JobDTO jobDTO = findById(idJob);
            if (jobDTO.getIdProfiePending() == null) {
                jobDTO.setIdProfiePending(new ArrayList<>());
            }
            List<Integer> idProfilePending = jobDTO.getIdProfiePending();
            profileClient.getProfileById(idProfile);
            idProfilePending.add(idProfile);
            jobDTO.setIdProfiePending(idProfilePending);
            return update(jobDTO);
        } catch (DataIntegrityViolationException e) {
            throw new CustomException(Error.COMPANY_UNABLE_TO_UPDATE);
        } catch (DataAccessException e) {
            System.err.println("Error while fetching companies by type: " + e.getMessage());
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public JobDTO acceptProfile(Integer idJob, Integer idProfile) {
        try {
            log.info("Accepting profile id: {}, job id: {}", idProfile, idJob);
            JobDTO jobDTO = findById(idJob);
            if (jobDTO.getIdProfiePending() != null) {
                jobDTO.getIdProfiePending().remove(idProfile);
            }
            if (jobDTO.getIdProfile() == null) {
                jobDTO.setIdProfile(new ArrayList<>());
            }
            List<Integer> idProfileJob = jobDTO.getIdProfile();
            // profileClient.checkIdProfile(idProfile);
            int size = jobDTO.getSize() - 1;
            jobDTO.setSize(size);
            idProfileJob.add(idProfile);
            jobDTO.setIdProfile(idProfileJob);
            JobDTO jobAccept = update(jobDTO);
            ProfileDTO profileDTO = profileClient.getProfileById(idProfile).getData();
            MessageDTO messageDTO = MessageDTO.builder().message("accept job successful by" + jobDTO.getTypeJob())
                    .id(profileDTO.getIdUser()).build();
            JobDTO jobDTO1=update(jobDTO);
            notificationClient.create(messageDTO);
            emailClient.send(messageDTO);
            return jobDTO1;
        } catch (DataIntegrityViolationException e) {
            throw new CustomException(Error.COMPANY_UNABLE_TO_UPDATE);
        } catch (DataAccessException e) {
            System.err.println("Error while fetching companies by type: " + e.getMessage());
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public JobDTO rejectProfile(Integer idJob, Integer idProfile) {
        try {
            log.info("Rejecting profile id: {}, job id: {}", idProfile, idJob);
            JobDTO jobDTO = findById(idJob);
            List<Integer> idProfilePending = jobDTO.getIdProfiePending();
            // Boolean checkIdProfile = profileClient.checkIdProfile(idProfile);
            idProfilePending.remove(idProfile);
            jobDTO.setIdProfiePending(idProfilePending);
            return update(jobDTO);
        } catch (DataIntegrityViolationException e) {
            throw new CustomException(Error.COMPANY_UNABLE_TO_UPDATE);
        } catch (DataAccessException e) {
            System.err.println("Error while fetching companies by type: " + e.getMessage());
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public List<JobDTO> getJobByPrfilePending(Integer id) {
        try {
            log.info("Fetching jobs by profile pending id: {}", id);
            Query query = new Query();
            query.addCriteria(Criteria.where("idProfiePending").is(id));
            return convertToListDTO(mongoTemplate.find(query, Job.class));
        } catch (MongoCommandException e) {
            throw new CustomException(Error.MONGO_QUERY_EXECUTION_ERROR);
        } catch (DataAccessException e) {
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public List<JobDTO> getJobByProfileAccepted(Integer id) {
        try {
            log.info("Fetching jobs by profile accepted id: {}", id);
            Query query = new Query();
            query.addCriteria(Criteria.where("idProfile").is(id));
            return convertToListDTO(mongoTemplate.find(query, Job.class));
        } catch (MongoCommandException e) {
            throw new CustomException(Error.MONGO_QUERY_EXECUTION_ERROR);
        } catch (DataAccessException e) {
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public List<JobDTO> getNewJob(Integer id) {
        try {
            log.info("Fetching new jobs for profile id: {}", id);
            Query query = new Query();
            query.addCriteria(
                    Criteria.where("idProfiePending").nin(id)
                            .and("idProfile").nin(id));
            return convertToListDTO(mongoTemplate.find(query, Job.class));
        } catch (MongoCommandException e) {
            throw new CustomException(Error.MONGO_QUERY_EXECUTION_ERROR);
        } catch (DataAccessException e) {
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public JobDTO delete(Integer id) {
        try {
            log.info("Deleting job by id: {}", id);
            Job job = jobRepository.findById(id).orElseThrow(() -> new CustomException(Error.JOB_NOT_FOUND));
            jobRepository.delete(job);
            return convertToDTO(job);
        } catch (DataAccessException e) {
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

}
