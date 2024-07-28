package com.baconbao.profile_service.repository;

import com.baconbao.profile_service.model.Profile;
import com.baconbao.profile_service.model.TypeProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile,Integer> {
    Optional<Profile> findById(Integer integer);
    List<Profile> findByTypeProfile(TypeProfile typeProfile);

}
