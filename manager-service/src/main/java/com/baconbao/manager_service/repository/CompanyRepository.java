package com.baconbao.manager_service.repository;

import com.baconbao.manager_service.models.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends MongoRepository<Company, Integer> {
}