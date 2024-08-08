package com.example.profile_hr_service.services.serviceimpl;

import com.example.profile_hr_service.dto.CompanyDTO;
import com.example.profile_hr_service.dto.JobDTO;
import com.example.profile_hr_service.exception.CustomException;
import com.example.profile_hr_service.exception.Error;
import com.example.profile_hr_service.model.Company;
import com.example.profile_hr_service.openFeign.ProfileClient;
import com.example.profile_hr_service.repository.CompanyRepository;
import com.example.profile_hr_service.services.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private ProfileClient profileClient;

    private Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0x7FFFFFFF);
    }

    private Company convertToModel(CompanyDTO companyDTO){
        return modelMapper.map(companyDTO, Company.class);
    }
    private CompanyDTO convertToDTO(Company company){
        return modelMapper.map(company, CompanyDTO.class);
    }
    private List<CompanyDTO> convertToListDTO(List<Company> companies){
        return companies.stream()
                .map(this::convertToDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    

    private List<JobDTO> convertToListJobDTO(List<Job> jobs){
        return jobs.stream()
                .map(job -> modelMapper.map(job, JobDTO.class))
                .collect(java.util.stream.Collectors.toList());
    }

    private Company save(CompanyDTO companyDTO){
        log.info("Inserting company");
        try{
            Company company = Company.builder()
                    .id(getGenerationId())
                    .name(companyDTO.getName())
                    .type(companyDTO.getType())
                    .description(companyDTO.getDescription())
                    .street(companyDTO.getStreet())
                    .city(companyDTO.getCity())
                    .phone(companyDTO.getPhone())
                    .email(companyDTO.getEmail())
                    .country(companyDTO.getCountry())
                    .build();
            return companyRepository.insert(company);
        }catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public CompanyDTO findById(Integer id) {
        log.info("Get company by id: {}", id);
        return convertToDTO(companyRepository.findById(id)
                .orElseThrow(()-> new CustomException(Error.COMPANY_NOT_FOUND)));
    }

    @Override
    public CompanyDTO create(CompanyDTO companyDTO) {
        return convertToDTO(save(companyDTO));
    }

    @Override
    public CompanyDTO update(CompanyDTO companyDTO) {
        try{
            log.info("Updating Company by id: {}", companyDTO.getId());
            return convertToDTO(companyRepository.save(convertToModel(companyDTO)));
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public void deleteById(Integer id) {
        try {
            log.info("Deleting Company by id: {}", id);
            companyRepository.deleteById(id);
        }catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public List<CompanyDTO> getCompanyDTOs() {
        try{
            log.info("Fetching all CompanyDTOs");
            Query query = new Query();
            query.limit(20);
            return convertToListDTO(mongoTemplate.find(query, Company.class));
        }  catch (DataAccessException e) {
            System.err.println("Error while fetching CompanyDTOs: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<CompanyDTO> getCompanyByType(String type) {
        try{
            log.info("Fetching companies by type: {}", type);
            Query query = new Query();
            query.addCriteria(Criteria.where("type").regex(type));
            query.limit(20);
            return convertToListDTO(mongoTemplate.find(query, Company.class));
        }  catch (DataAccessException e) {
            // Log the error message
            System.err.println("Error while fetching companies by type: " + e.getMessage());
            // Return an empty list in case of an error
            return Collections.emptyList();
        }
    }

    @Override
    public List<JobDTO> getJobByCompany(Integer id) {
        try {
            log.info("Fetching jobs by company id: {}", id);
            Query query = new Query();
            query.addCriteria(Criteria.where("idCompany").is(id));
            return convertToListJobDTO(mongoTemplate.find(query, Job.class));
        } catch (DataAccessException e) {
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public JobDTO setIdProfilePendingToJob(JobDTO jobDTO, Integer idProfile) {
      if(profileClient.checkIdProfile(idProfile)){
          
      }
        return null;
    }

    @Override
    public JobDTO setIdProfileToJob(JobDTO jobDTO, Integer idProfile) {
        if(profileClient.checkIdProfile(idProfile)){
            JobDTO jobDTO2 = companyRepository.findOne(convertToModel(jobDTO));
               //xóa idProfiePending vaf thêm vào idProfile
            //giam size
        }
        return null;
    }
}
