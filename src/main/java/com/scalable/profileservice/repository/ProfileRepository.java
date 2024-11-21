package com.scalable.profileservice.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.scalable.profileservice.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findByUserId(Long userId);  // Query by userId
}

