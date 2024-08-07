package com.baconbao.profile_service.controller;

import com.baconbao.profile_service.dto.ProfileDTO;
import com.baconbao.profile_service.model.TypeProfile;
import com.baconbao.profile_service.services.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("profile")
@RestController
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping("/save")
    public ResponseEntity<ProfileDTO> save(@RequestBody ProfileDTO profileDTO) {
        return ResponseEntity.ok(profileService.saveProfile(profileDTO));
    }

    @PostMapping("/update")
    public ResponseEntity<ProfileDTO> update(@RequestBody ProfileDTO profileDTO) {
        return ResponseEntity.ok(profileService.updateProfile(profileDTO));
    }

    @GetMapping("/findProfileByType")
    public ResponseEntity<List<ProfileDTO>> findProfilesByType(@RequestParam String typeProfile) {
        return ResponseEntity.ok(profileService.findProfilesByType(TypeProfile.valueOf(typeProfile)));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ProfileDTO>> getAll() {
        return ResponseEntity.ok(profileService.getAllProfile());
    }
    @GetMapping("/findById")
    public ResponseEntity<ProfileDTO> getProfileById(@RequestParam Integer id) {
        return ResponseEntity.ok(profileService.findById(id));
    }
    @GetMapping("/findByTitle")
    public ResponseEntity<List<ProfileDTO>> findByTitle(@RequestParam String title) {
        return ResponseEntity.ok(profileService.findByTitle(title));
    }
    @GetMapping("/checkIdProfile")
    public ResponseEntity<Boolean> checkIdProfie(@RequestParam Integer id) {
        return ResponseEntity.ok(profileService.checkIdProfile(id));
    }

}
