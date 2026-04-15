package com.example.csrfdemo.broken_access_control;

public class SafeUserDto {
    public String username;
    public String email;
    public String role;

    public SafeUserDto(User user) {
        this.username = user.username;
        this.email = user.email;
        this.role = user.role;
    }
}