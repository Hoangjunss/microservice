package com.baconbao.manager_service.controller;


import com.baconbao.manager_service.dto.ApiResponse;
import com.baconbao.manager_service.dto.JobDTO;
import com.baconbao.manager_service.services.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequestMapping("/manager")
@RestController
public class JobController {
    @Autowired
    private JobService jobService;

    @PostMapping("/job/create")
    public ResponseEntity<ApiResponse<JobDTO>> create(@RequestBody JobDTO job){
        JobDTO createdJob = jobService.create(job);
        return ResponseEntity.ok(new ApiResponse<>(true, "Job created", createdJob));
    }

    @PostMapping("/job/update")
    public ResponseEntity<ApiResponse<JobDTO>> update(@RequestBody JobDTO job){
        JobDTO updatedJob = jobService.update(job);
        return ResponseEntity.ok(new ApiResponse<>(true, "Job updated", updatedJob));
    }

    @PostMapping("/job/delete")
    public ResponseEntity<ApiResponse<String>> delete(@RequestParam Integer id){
        jobService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Job deleted", "ok"));
    }

    @GetMapping("/job/findbyid")
    public ResponseEntity<ApiResponse<JobDTO>> getById(Integer id){
        JobDTO job = jobService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Job found", job));
    }

    @GetMapping("/job/getjobbycompany")
    public ResponseEntity<ApiResponse<List<JobDTO>>> getJobByCompany(@RequestParam Integer id){
        List<JobDTO> jobs = jobService.getJobByCompany(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Jobs found", jobs));
    }

    @PutMapping("/job/apply")
    public ResponseEntity<ApiResponse<JobDTO>> apply(@RequestBody JobDTO jobDTO, Integer idProfile) {
        JobDTO job = jobService.acceptProfile(jobDTO, idProfile);
        return ResponseEntity.ok(new ApiResponse<>(true, "Job applied", job));
    }

    @PutMapping("/job/accept")
    public ResponseEntity<ApiResponse<JobDTO>> accept(@RequestBody JobDTO jobDTO, Integer idProfile) {
        JobDTO job = jobService.acceptProfile(jobDTO, idProfile);
        return ResponseEntity.ok(new ApiResponse<>(true, "Job accepted", job));
    }

    @PutMapping("/job/reject")
    public ResponseEntity<ApiResponse<JobDTO>> reject(@RequestBody JobDTO jobDTO, Integer idProfile) {
        JobDTO job = jobService.rejectProfile(jobDTO, idProfile);
        return ResponseEntity.ok(new ApiResponse<>(true, "Job rejected", job));
    }


}
