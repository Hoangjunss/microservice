package com.baconbao.project_service.controller;

import com.baconbao.project_service.dto.ApiResponse;
import com.baconbao.project_service.dto.ProfileDTO;
import com.baconbao.project_service.dto.ProjectDTO;
import com.baconbao.project_service.services.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<ProjectDTO>> save(@RequestBody ProjectDTO projectDTO) {
        ProjectDTO savedProject = projectService.saveProject(projectDTO);
        ApiResponse<ProjectDTO> response = new ApiResponse<>(true, "Project saved successfully", savedProject);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update")
    public ResponseEntity<ApiResponse<ProjectDTO>> update(@RequestBody ProjectDTO projectDTO) {
        ProjectDTO updatedProject = projectService.updateProject(projectDTO);
        ApiResponse<ProjectDTO> response = new ApiResponse<>(true, "Project updated successfully", updatedProject);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getProfile")
    public ResponseEntity<ApiResponse<List<ProfileDTO>>> getProfile() {
        List<ProfileDTO> profiles = projectService.getAlliProfile();
        ApiResponse<List<ProfileDTO>> response = new ApiResponse<>(true, "Profiles fetched successfully", profiles);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getProject")
    public ResponseEntity<ApiResponse<List<ProjectDTO>>> getProjectByIdProfile(@RequestParam Integer id) {
        List<ProjectDTO> projects = projectService.getProjectByIdProfile(id);
        ApiResponse<List<ProjectDTO>> response = new ApiResponse<>(true, "Projects fetched successfully", projects);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<String>> get() {
        ApiResponse<String> response = new ApiResponse<>(true, "ok", "ok");
        return ResponseEntity.ok(response);
    }

}
