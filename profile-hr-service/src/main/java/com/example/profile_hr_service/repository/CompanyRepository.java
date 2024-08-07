package com.example.profile_hr_service.repository;

import com.example.profile_hr_service.model.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends MongoRepository<Company, Integer> {
}
