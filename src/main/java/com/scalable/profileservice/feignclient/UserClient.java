package com.scalable.profileservice.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "http://localhost:8082/api") // URL pointing to User Service
public interface UserClient {

    // Endpoint to check if the user exists
    @GetMapping("/users/{userId}")
    ResponseEntity<UserExistResponse> doesUserExist(@PathVariable("userId") Long userId);
}
