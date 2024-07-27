package com.baconbao.project_service.services.service;

import com.baconbao.project_service.dto.ProjectDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProjectService {
    ProjectDTO saveProject(ProjectDTO projectDTO);
    ProjectDTO updateProject(ProjectDTO projectDTO);
    ProjectDTO findById(ProjectDTO projectDTO);
    List<ProjectDTO> getAllProjectDTOByProfile(Integer idProfile);
}
