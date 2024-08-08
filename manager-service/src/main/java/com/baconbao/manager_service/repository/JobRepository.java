package com.baconbao.manager_service.repository;

import com.baconbao.manager_service.models.Job;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobRepository extends MongoRepository<Job, Integer> {

}
