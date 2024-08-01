package com.baconbao.profile_service.repository;

import com.baconbao.profile_service.model.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ProfileRepository extends MongoRepository<Profile,Integer> {
}
