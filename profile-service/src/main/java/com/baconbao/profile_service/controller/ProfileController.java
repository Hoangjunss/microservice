package com.baconbao.profile_service.controller;

import com.baconbao.profile_service.dto.ProfileDTO;
import com.baconbao.profile_service.model.TypeProfile;
import com.baconbao.profile_service.services.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/save")
    public ResponseEntity<ProfileDTO> save(@RequestBody ProfileDTO profileDTO){
        return ResponseEntity.ok(profileService.saveProfile(profileDTO));
    }
    @PostMapping("/update")
    public ResponseEntity<ProfileDTO> update(@RequestBody ProfileDTO profileDTO){
        return ResponseEntity.ok(profileService.updateProfile(profileDTO));
    }
    @GetMapping("/findProfileByType")
    public ResponseEntity<List<ProfileDTO>> findProfilesByType(@RequestParam String typeProfile){
        return ResponseEntity.ok(profileService.findProfilesByType(TypeProfile.valueOf(typeProfile)));
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<ProfileDTO>> getAll(){
        return ResponseEntity.ok(profileService.getAllProfile());
    }
}
