package com.securityApp.secure.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import org.springframework.stereotype.Component;

import java.util.Set;
@Component
public class UserRegistration {

    private String userName;
    private String firstName;
    private String lastName;
    private String userPassword;
    private byte[] photo;
    private Set<Role> role;

    public UserRegistration(String userName, String firstName, String lastName, String userPassword, byte[] photo, Set<Role> role) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userPassword = userPassword;
        this.photo = photo;
        this.role = role;
    }

    public UserRegistration() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Set<Role> getRole() {
        return role;
    }

    public void setRole(Set<Role> role) {
        this.role = role;
    }
}
