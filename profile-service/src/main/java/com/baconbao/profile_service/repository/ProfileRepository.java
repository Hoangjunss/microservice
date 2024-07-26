package com.baconbao.profile_service.repository;

import com.baconbao.profile_service.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile,Integer> {
}
