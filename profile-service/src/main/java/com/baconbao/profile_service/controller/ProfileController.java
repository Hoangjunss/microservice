package com.baconbao.profile_service.controller;

import com.baconbao.profile_service.dto.ProfileDTO;
import com.baconbao.profile_service.services.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    @PostMapping("/save")
    public ResponseEntity<ProfileDTO> save(@RequestBody ProfileDTO profileDTO){
        return ResponseEntity.ok(profileService.saveProfile(profileDTO));
    }
    @PostMapping("/update")
    public ResponseEntity<ProfileDTO> update(@RequestBody ProfileDTO profileDTO){
        return ResponseEntity.ok(profileService.updateProfile(profileDTO));
    }
}
