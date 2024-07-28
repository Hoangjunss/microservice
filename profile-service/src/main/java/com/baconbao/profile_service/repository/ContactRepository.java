package com.baconbao.profile_service.repository;

import com.baconbao.profile_service.model.Contact;
import com.baconbao.profile_service.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact,Integer> {

}
