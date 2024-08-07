package com.baconbao.profile_service.controller;

import com.baconbao.profile_service.dto.ApiResponse;
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
    public ResponseEntity<ApiResponse<ProfileDTO>> save(@RequestBody ProfileDTO profileDTO) {
        ProfileDTO resultProfileDTO = profileService.saveProfile(profileDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "Profile saved successfully", resultProfileDTO));
    }

    @PostMapping("/update")
    public ResponseEntity<ApiResponse<ProfileDTO>> update(@RequestBody ProfileDTO profileDTO) {
        ProfileDTO resultProfileDTO = profileService.updateProfile(profileDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "Profile update successfully", resultProfileDTO));
    }

    @GetMapping("/findProfileByType")
    public ResponseEntity<ApiResponse<List<ProfileDTO>>> findProfilesByType(@RequestParam String typeProfile) {
        List<ProfileDTO> resultProfiles = profileService.findProfilesByType(TypeProfile.valueOf(typeProfile));
        return ResponseEntity.ok(new ApiResponse<>(true, "Find Profile By Type", resultProfiles));
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<ProfileDTO>>> getAll() {
        List<ProfileDTO> resultProfiles = profileService.getAllProfile();
        return ResponseEntity.ok(new ApiResponse<>(true, "Get all is successfully", resultProfiles));
    }
    @GetMapping("/findById")
    public ResponseEntity<ApiResponse<ProfileDTO>> getProfileById(@RequestParam Integer id) {
        ProfileDTO profileDTO = profileService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Find by id is successfully", profileDTO));
    }
    @GetMapping("/findByTitle")
    public ResponseEntity<ApiResponse<List<ProfileDTO>>> findByTitle(@RequestParam String title) {
        List<ProfileDTO> resultProfiles = profileService.findByTitle(title);
        return ResponseEntity.ok(new ApiResponse<>(true, "Find by title is successfully", resultProfiles));
    }
    @GetMapping("/checkIdProfile")
    public ResponseEntity<ApiResponse<Boolean>> checkIdProfie(@RequestParam Integer id) {
        boolean result = profileService.checkIdProfile(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Check id profile", result));
    }

}
