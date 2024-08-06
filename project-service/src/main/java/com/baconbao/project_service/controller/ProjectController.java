package com.baconbao.project_service.controller;

import com.baconbao.project_service.dto.ProfileDTO;
import com.baconbao.project_service.dto.ProjectDTO;
import com.baconbao.project_service.services.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @PostMapping("/save")
    public ResponseEntity<ProjectDTO> save(@RequestBody ProjectDTO projectDTO) {
        return ResponseEntity.ok(projectService.saveProject(projectDTO));
    }

    @PostMapping("/update")
    public ResponseEntity<ProjectDTO> update(@RequestBody ProjectDTO projectDTO) {
        return ResponseEntity.ok(projectService.updateProject(projectDTO));
    }

    @GetMapping("/getProfile")
    public ResponseEntity<List<ProfileDTO>> getProfile() {
        return ResponseEntity.ok(projectService.getAlliProfile());
    }

    @GetMapping("/getProject")
    public ResponseEntity<List<ProjectDTO>> getMethodName(@RequestParam Integer id) {
        return ResponseEntity.ok(projectService.getProjectByIdProfile(id));
    }

    @GetMapping("/get")
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("ok");
    }

}
