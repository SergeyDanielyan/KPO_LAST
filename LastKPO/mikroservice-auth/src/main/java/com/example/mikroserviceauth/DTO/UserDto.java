package com.example.mikroserviceauth.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private String email;
    private String nickname;
    private String password;
    private LocalDateTime created;

    @Override
    public String toString() {
        return "User\n" + email + "\n" + nickname + "\n" + password + "\n";
    }
}
