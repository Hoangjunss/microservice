package com.example.profile_hr_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.profile_hr_service.model.Job;

public interface JobRepository extends MongoRepository<Job, Integer>{
    
}
