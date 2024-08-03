package com.baconbao.project_service.repository;

import com.baconbao.project_service.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ProjectRepository extends JpaRepository<Project,Integer> {
    List<Project> findByIdProfile(Integer idProfile);
}
