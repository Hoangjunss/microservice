package com.baconbao.profile_service.controller;

import com.baconbao.profile_service.dto.ApiResponse;
import com.baconbao.profile_service.dto.BooleanDTO;
import com.baconbao.profile_service.dto.ProfileDTO;
import com.baconbao.profile_service.model.Profile;
import com.baconbao.profile_service.model.TypeProfile;
import com.baconbao.profile_service.services.service.ProfileService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("profile")
@RestController
@Slf4j
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping("/user/save")
    public ResponseEntity<ApiResponse<ProfileDTO>> save(@ModelAttribute ProfileDTO profileDTO, @RequestPart(value="image",required = false)MultipartFile image) {
        ProfileDTO resultProfileDTO = profileService.saveProfile(profileDTO,image);
        return ResponseEntity.ok(new ApiResponse<>(true, "Profile saved successfully", resultProfileDTO));
    }

    @PostMapping(value = "/user/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ProfileDTO>> update(@ModelAttribute ProfileDTO profileDTO, @RequestPart(value="image",required = false)MultipartFile image) {
        log.info("ProfileDTO: {}", profileDTO);
        log.info("iamge :{}", image);
        ProfileDTO resultProfileDTO = profileService.updateProfile(profileDTO,image);
        return ResponseEntity.ok(new ApiResponse<>(true, "Profile update successfully", resultProfileDTO));
    }

    @GetMapping("/user/findProfileByType")
    public ResponseEntity<ApiResponse<List<ProfileDTO>>> findProfilesByType(@RequestParam String typeProfile) {
        List<ProfileDTO> resultProfiles = profileService.findProfilesByType(TypeProfile.valueOf(typeProfile));
        return ResponseEntity.ok(new ApiResponse<>(true, "Find Profile By Type", resultProfiles));
    }

    @GetMapping("/user/getAll")
    public ResponseEntity<ApiResponse<List<ProfileDTO>>> getAll() {
        List<ProfileDTO> resultProfiles = profileService.getAllProfile();
        return ResponseEntity.ok(new ApiResponse<>(true, "Get all is successfully", resultProfiles));
    }
    @GetMapping("/user/findById")
    public ResponseEntity<ApiResponse<ProfileDTO>> getProfileById(@RequestParam Integer id) {
        ProfileDTO profileDTO = profileService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Find by id is successfully", profileDTO));
    }
    
    @GetMapping("/user/findByUserId")
    public ResponseEntity<ApiResponse<ProfileDTO>> findByUserId(@RequestParam Integer userId) {
        ProfileDTO resultProfiles = profileService.findByIdUser(userId);
        if(resultProfiles!=null){
            return ResponseEntity.ok(new ApiResponse<>(true, "Find by user id is successfully", resultProfiles));
        }
        return ResponseEntity.ok(new ApiResponse<>(false, "Profile not found", null));
    }

    @GetMapping("/user/findByTitle")
    public ResponseEntity<ApiResponse<List<ProfileDTO>>> findByTitle(@RequestParam String title) {
        List<ProfileDTO> resultProfiles = profileService.findByTitle(title);
        return ResponseEntity.ok(new ApiResponse<>(true, "Find by title is successfully", resultProfiles));
    }
    
    @GetMapping("/user/checkIdProfile")
    public ResponseEntity<ApiResponse<String>> checkIdProfie(@RequestParam Integer id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Check id profile", "true"));
    }

    @GetMapping("/manager/getProfileByIdPendingJob")
    public ResponseEntity<ApiResponse<List<ProfileDTO>>> getProfileByIdPendingJob(@RequestParam List<Integer> ids) {
        List<ProfileDTO> resultProfiles = profileService.findListProfileByIdPendingJob(ids);
        return ResponseEntity
                .ok(new ApiResponse<>(true, "Profiles retrieved successfully by pending job id", resultProfiles));
    } 

}
