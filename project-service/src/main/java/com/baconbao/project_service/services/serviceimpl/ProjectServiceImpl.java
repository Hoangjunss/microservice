package com.baconbao.project_service.services.serviceimpl;

import com.baconbao.project_service.dto.ProjectDTO;
import com.baconbao.project_service.model.Project;
import com.baconbao.project_service.repository.ProjectRepository;
import com.baconbao.project_service.services.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ModelMapper modelMapper;

    private Project save(ProjectDTO projectDTO) {
        Project project=Project.builder()
                .id(getGenerationId())
                .title(projectDTO.getTitle())
                .description(projectDTO.getDescription())
                .url(projectDTO.getUrl())
                .build();
        return projectRepository.save(project);
    }
    public Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        // Use most significant bits and ensure it's within the integer range
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }

    @Override
    public ProjectDTO saveProject(ProjectDTO projectDTO) {
        log.info("Save project");
        Project project=save(projectDTO);
        //profileService.updateProjectByProfile(project,projectDTO.getIdProfile());
        return convertToDTO(project);
    }

    @Override
    public ProjectDTO updateProject(ProjectDTO projectDTO) {
        log.info("Update project");
        projectDTO.setCreateAt(findById(projectDTO).getCreateAt());
        Project project=projectRepository.save(convertToModel(projectDTO));

        return convertToDTO(project);
    }

    @Override
    public ProjectDTO findById(ProjectDTO projectDTO) {
        log.info("Find project by id: {}", projectDTO.getId());
        return convertToDTO(projectRepository.findById(projectDTO.getId())
                .orElseThrow());
    }

    @Override
    public List<ProjectDTO> getAllProjectDTOByProfile(Integer idProfile) {
        log.info("Find all projects by idProfile: {}", idProfile);
        List<ProjectDTO> projectDTOS = new ArrayList<>();
        for (Project project : projectRepository.getProjectByProfile(idProfile)) {
            projectDTOS.add(convertToDTO(project));
        }
        return projectDTOS;
    }

    private ProjectDTO convertToDTO(Project project) {
        return modelMapper.map(project, ProjectDTO.class);
    }

    private Project convertToModel(ProjectDTO projectDTO) {
        return modelMapper.map(projectDTO, Project.class);
    }

    public List<ProjectDTO> convertToDTOList(List<Project> projects) {

        return projects.stream()
                .map(project -> modelMapper.map(project, ProjectDTO.class))
                .collect(Collectors.toList());
    }

}
