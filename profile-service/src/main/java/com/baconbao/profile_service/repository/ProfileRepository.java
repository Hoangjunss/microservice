package com.baconbao.profile_service.repository;

import com.baconbao.profile_service.model.Profile;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface ProfileRepository extends MongoRepository<Profile,Integer> {
    List<Profile> findByIdIn(List<Integer> ids);
}
