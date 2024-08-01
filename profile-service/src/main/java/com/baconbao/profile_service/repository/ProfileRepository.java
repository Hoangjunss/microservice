package com.baconbao.profile_service.repository;

import com.baconbao.profile_service.model.Profile;
import com.baconbao.profile_service.model.TypeProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends MongoRepository<Profile,Integer> {
    Optional<Profile> findById(Integer integer);
    List<Profile> findByTypeProfile(TypeProfile typeProfile);
}
