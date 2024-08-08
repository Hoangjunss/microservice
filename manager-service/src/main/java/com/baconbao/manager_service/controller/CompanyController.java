package com.baconbao.manager_service.controller;


import com.baconbao.manager_service.dto.ApiResponse;
import com.baconbao.manager_service.dto.CompanyDTO;
import com.baconbao.manager_service.services.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("manager")
@RestController
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @PostMapping("/company/create")
    public ResponseEntity<ApiResponse<CompanyDTO>> create(@RequestBody CompanyDTO companyDTO){
        CompanyDTO companyDTO1 = companyService.create(companyDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "Company created successfully", companyDTO1));
    }

    @PostMapping("/company/update")
    public ResponseEntity<ApiResponse<CompanyDTO>> update(@RequestBody CompanyDTO companyDTO){
        CompanyDTO companyDTO1 = companyService.update(companyDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "Company updated successfully", companyDTO1));
    }

    @PostMapping("/company/delete")
    public ResponseEntity<ApiResponse<String>> delete(@RequestParam Integer id){
        companyService.deleteById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Company deleted successfully", "ok"));
    }

    @GetMapping("/company/getbyid")
    public ResponseEntity<ApiResponse<CompanyDTO>> getById(@RequestParam Integer id){
        CompanyDTO companyDTO = companyService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Company retrieved successfully", companyDTO));
    }

    @GetMapping("/company/getcompany")
    public ResponseEntity<ApiResponse<List<CompanyDTO>>> getAllCompanies(){
        List<CompanyDTO> companyDTOs = companyService.getCompanyDTOs();
        return ResponseEntity.ok(new ApiResponse<>(true, "Companies retrieved successfully", companyDTOs));
    }

    @GetMapping("/company/getcompanybytype")
    public ResponseEntity<ApiResponse<List<CompanyDTO>>> getCompanyByType(@RequestParam String type){
        List<CompanyDTO> companyDTOs = companyService.getCompanyByType(type);
        return ResponseEntity.ok(new ApiResponse<>(true, "Companies retrieved successfully by type", companyDTOs));
    }


}
