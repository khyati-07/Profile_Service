package com.scalable.profileservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long profileid;
    private Long userId;
    private String username;

    @JsonIgnore
    private String password;

    private String readingPreference;
    private String favouriteGenre;
    private String booksOwned;
    private String wishList;

    // Getters and Setters

    public Long getUserId() {  // Add getter for userId
        return userId;
    }

    public void setUserId(Long userId) {  // Add setter for userId
        this.userId = userId;
    }

   

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReadingPreference() {
        return readingPreference;
    }

    public void setReadingPreference(String readingPreference) {
        this.readingPreference = readingPreference;
    }

    public String getFavouriteGenre() {
        return favouriteGenre;
    }

    public void setFavouriteGenre(String favouriteGenre) {
        this.favouriteGenre = favouriteGenre;
    }

    public String getBooksOwned() {
        return booksOwned;
    }

    public void setBooksOwned(String booksOwned) {
        this.booksOwned = booksOwned;
    }

    public String getWishList() {
        return wishList;
    }

    public void setWishList(String wishList) {
        this.wishList = wishList;
    }
}
