package com.baconbao.project_service.services.service;

import com.baconbao.project_service.dto.ProfileDTO;
import com.baconbao.project_service.dto.ProjectDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProjectService {
    ProjectDTO saveProject(ProjectDTO projectDTO);
    ProjectDTO updateProject(ProjectDTO projectDTO);
    ProjectDTO findById(Integer id);
    List<ProjectDTO> getAllProjectDTOByProfile(Integer idProfile);
    List<ProfileDTO>getSlliProfile();
}
