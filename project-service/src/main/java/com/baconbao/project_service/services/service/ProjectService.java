package com.baconbao.project_service.services.service;

import com.baconbao.project_service.dto.ImageDTO;
import com.baconbao.project_service.dto.ProfileDTO;
import com.baconbao.project_service.dto.ProjectDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProjectService {
    ProjectDTO saveProject(ProjectDTO projectDTO);
    ProjectDTO updateProject(ProjectDTO projectDTO);
    ProjectDTO findById(Integer id);

    List<ProfileDTO>getAlliProfile();
    List<ProjectDTO>getProjectByIdProfile(Integer id);
    ImageDTO getall(MultipartFile multipartFile);
}
