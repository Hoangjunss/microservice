package com.example.profile_hr_service.controller;

import com.example.profile_hr_service.dto.ApiResponse;
import com.example.profile_hr_service.dto.CompanyDTO;
import com.example.profile_hr_service.services.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("company")
@RestController
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CompanyDTO>> create(@RequestBody CompanyDTO companyDTO){
        CompanyDTO companyDTO1 = companyService.create(companyDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "Create is successful", companyDTO1));
    }

    @PostMapping("/update")
    public ResponseEntity<ApiResponse<CompanyDTO>> update(@RequestBody CompanyDTO companyDTO){
        CompanyDTO companyDTO1 = companyService.update(companyDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "Update is success", companyDTO1));
    }

    @PostMapping("/delete")
    public ResponseEntity<ApiResponse<String>> delete(@RequestBody CompanyDTO companyDTO){
        companyService.deleteById(companyDTO.getId());
        return ResponseEntity.ok(new ApiResponse<>(true, "Delete is success", "ok"));
    }

    @GetMapping("/getall")
    public ResponseEntity<ApiResponse<List<CompanyDTO>>> getAll(){
        List<CompanyDTO> companyDTOS = companyService.getCompanyDTOs();
         return ResponseEntity.ok(new ApiResponse<>(true, "Get all", companyDTOS));
    }

    @GetMapping("/getbyid")
    public ResponseEntity<ApiResponse<CompanyDTO>> getById(@RequestParam Integer id){
        CompanyDTO companyDTO = companyService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Get by id", companyDTO));
    }

    @GetMapping("/getbytype")
    public ResponseEntity<ApiResponse<List<CompanyDTO>>> getByType(@RequestParam String type){
        List<CompanyDTO> companyDTOS = companyService.getCompanyByType(type);
        return ResponseEntity.ok(new ApiResponse<>(true, "Get by type is success", companyDTOS));
    }
}
