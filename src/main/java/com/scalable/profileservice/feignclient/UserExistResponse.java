package com.scalable.profileservice.feignclient;

public class UserExistResponse {
    
    private Long id;
    private String username;
    private String email;
    private String password;

    // Getter and Setter for id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter and Setter for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter and Setter for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and Setter for password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Method to check if the user exists based on id
    public boolean isExists() {
        return id != null;  // If id is present, the user exists
    }
}
