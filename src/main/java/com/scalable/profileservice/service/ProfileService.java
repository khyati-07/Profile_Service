package com.scalable.profileservice.service;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.scalable.profileservice.entity.Profile;
import com.scalable.profileservice.feignclient.UserClient;
import com.scalable.profileservice.feignclient.UserExistResponse;
import com.scalable.profileservice.repository.ProfileRepository;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserClient userClient;

    // Method to save a new profile
    public Profile saveProfile(Profile profile) {
        try {
            // Call User service to check if the user exists by calling the Feign Client
            ResponseEntity<UserExistResponse> response = userClient.doesUserExist(profile.getUserId());
            
            if (response.getStatusCode() == HttpStatus.OK && response.getBody().isExists()) {
                // User exists, save the profile in the Profile service's database
                return profileRepository.save(profile);
            } else {
                // If the user doesn't exist, throw an exception
                throw new RuntimeException("User does not exist.");
            }
        } catch (FeignException.NotFound e) {
            // Handle Feign exception if the user does not exist or the service is unavailable
            throw new RuntimeException("User with ID " + profile.getUserId() + " does not exist.");
        } catch (FeignException e) {
            // Handle other Feign-related errors (e.g., network issues, service down)
            throw new RuntimeException("An error occurred while communicating with the User service.");
        }
    }

    // Method to retrieve a profile by userId
    public Profile getProfileByUserId(Long userId) {
        try {
            // Check if the user exists before retrieving the profile
            ResponseEntity<UserExistResponse> response = userClient.doesUserExist(userId);
            
            if (response.getStatusCode() != HttpStatus.OK || !response.getBody().isExists()) {
                // If the user doesn't exist, throw an exception
                throw new RuntimeException("User with ID " + userId + " does not exist.");
            }
            
            // Return the profile associated with the userId
            return profileRepository.findByUserId(userId);
        } catch (FeignException.NotFound e) {
            // Handle Feign exception if the user does not exist or the service is unavailable
            throw new RuntimeException("User with ID " + userId + " does not exist.");
        } catch (FeignException e) {
            // Handle other Feign-related errors (e.g., network issues, service down)
            throw new RuntimeException("An error occurred while communicating with the User service.");
        }
    }

    // Method to update an existing profile by userId
    public Profile updateProfile(Long userId, Profile updatedProfile) {
        try {
            // Check if the user exists by calling the User service
            ResponseEntity<UserExistResponse> response = userClient.doesUserExist(userId);
            
            if (response.getStatusCode() != HttpStatus.OK || !response.getBody().isExists()) {
                // If the user doesn't exist, throw an exception
                throw new RuntimeException("User with ID " + userId + " does not exist.");
            }
            
            // Check if the profile exists
            Profile existingProfile = profileRepository.findByUserId(userId);
            if (existingProfile == null) {
                // If the profile doesn't exist, throw an exception
                throw new RuntimeException("Profile with userId " + userId + " does not exist.");
            }
            
            // Update the profile fields
            existingProfile.setUsername(updatedProfile.getUsername());
            existingProfile.setReadingPreference(updatedProfile.getReadingPreference());
            existingProfile.setFavouriteGenre(updatedProfile.getFavouriteGenre());
            existingProfile.setBooksOwned(updatedProfile.getBooksOwned());
            existingProfile.setWishList(updatedProfile.getWishList());
            
            // Save and return the updated profile
            return profileRepository.save(existingProfile);
        } catch (FeignException.NotFound e) {
            // Handle Feign exception if the user does not exist or the service is unavailable
            throw new RuntimeException("User with ID " + userId + " does not exist.");
        } catch (FeignException e) {
            // Handle other Feign-related errors (e.g., network issues, service down)
            throw new RuntimeException("An error occurred while communicating with the User service.");
        }
    }
}
