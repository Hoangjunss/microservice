package com.baconbao.user_service.repository;


import com.baconbao.user_service.model.User;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
   Optional<User> findByEmail(String email);
   Optional<User> findById(Integer id);
   List<User> findAll();

}
