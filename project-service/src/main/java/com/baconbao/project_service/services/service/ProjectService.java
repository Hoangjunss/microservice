package com.baconbao.project_service.services.service;

import com.baconbao.project_service.dto.ProfileDTO;
import com.baconbao.project_service.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {
    ProjectDTO saveProject(ProjectDTO projectDTO);
    ProjectDTO updateProject(ProjectDTO projectDTO);
    ProjectDTO findById(Integer id);

    List<ProfileDTO>getAlliProfile();
    List<ProjectDTO>getProjectByIdProfile(Integer id);
}
