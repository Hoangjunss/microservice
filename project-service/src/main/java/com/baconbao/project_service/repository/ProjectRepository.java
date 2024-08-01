package com.baconbao.project_service.repository;

import com.baconbao.project_service.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Integer> {
    @Query("SELECT p FROM project p WHERE p.idProfile = :idProfile")
    List<Project> getProjectByProfile(@Param("idProfile") Integer idProfile);

}
