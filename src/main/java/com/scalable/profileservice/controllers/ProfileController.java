package com.scalable.profileservice.controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scalable.profileservice.entity.Profile;
import com.scalable.profileservice.service.ProfileService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    // Get Profile by userId
    @GetMapping("/profile/{userId}")
    public ResponseEntity<?> getProfileByUserId(@PathVariable Long userId) {
        try {
            Profile profile = profileService.getProfileByUserId(userId);

            if (profile == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "User with ID " + userId + " does not exist."));
            }

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("message", "Profile retrieved successfully");
            response.put("data", profile);

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }

    // Save Profile (Create Profile)
    @PostMapping("/profile")
    public ResponseEntity<?> saveProfile(@RequestBody Profile profile) {
        try {
            // Check if profile already exists
            Profile existingProfile = profileService.getProfileByUserId(profile.getUserId());
            if (existingProfile != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("message", "Profile already exists."));
            }

            // Save new profile
            Profile savedProfile = profileService.saveProfile(profile);
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("message", "Profile created successfully");
            response.put("data", savedProfile);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    // Update Profile
    @PutMapping("/profile/{userId}")
    public ResponseEntity<?> updateProfile(@PathVariable Long userId, @RequestBody Profile profile) {
        try {
            // Check if the profile exists
            Profile existingProfile = profileService.getProfileByUserId(userId);
            if (existingProfile == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "User with ID " + userId + " does not exist."));
            }

            // Update profile
            Profile updatedProfile = profileService.updateProfile(userId, profile);
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("message", "Profile updated successfully");
            response.put("data", updatedProfile);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }
}
