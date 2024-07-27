package com.baconbao.project_service.controller;

import com.baconbao.project_service.dto.ProjectDTO;
import com.baconbao.project_service.services.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @PostMapping ("/save")
    public ResponseEntity<ProjectDTO> save(@RequestBody ProjectDTO projectDTO){
        return ResponseEntity.ok(projectService.saveProject(projectDTO));
    }
    @PostMapping("/update")
    public ResponseEntity<ProjectDTO> update(@RequestBody ProjectDTO projectDTO){
        return ResponseEntity.ok(projectService.updateProject(projectDTO));
    }
}
