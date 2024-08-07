package com.example.profile_hr_service.controller;

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
    public ResponseEntity<CompanyDTO> create(@RequestBody CompanyDTO companyDTO){
        return ResponseEntity.ok(companyService.create(companyDTO));
    }

    @PostMapping("/update")
    public ResponseEntity<CompanyDTO> update(@RequestBody CompanyDTO companyDTO){
        return ResponseEntity.ok(companyService.update(companyDTO));
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> delete(@RequestBody CompanyDTO companyDTO){
        companyService.deleteById(companyDTO.getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getall")
    public ResponseEntity<List<CompanyDTO>> getAll(){
        return ResponseEntity.ok(companyService.getCompanyDTOs());
    }

    @GetMapping("/getbyid")
    public ResponseEntity<CompanyDTO> getById(@RequestParam Integer id){
        return ResponseEntity.ok(companyService.findById(id));
    }

    @GetMapping("/getbytype")
    public ResponseEntity<List<CompanyDTO>> getByType(@RequestParam String type){
        return ResponseEntity.ok(companyService.getCompanyByType(type));
    }
}
