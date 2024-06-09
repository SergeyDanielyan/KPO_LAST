package com.example.mikroserviceauth.controllers;

import com.example.mikroserviceauth.DTO.UserDto;
import com.example.mikroserviceauth.models.Session;
import com.example.mikroserviceauth.models.User;
import com.example.mikroserviceauth.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "users_methods")
@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "пробует сохранить нового пользователя в базе данных"
    )
    @PostMapping("/registr")
    public ResponseEntity<String> addUser(@RequestBody UserDto userDto) {
        try {
            if (!userService.createUser(userDto)) {
                throw new Exception("Couldn't create such user");
            }
            return ResponseEntity.ok("User is registrated");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            summary = "пробует авторизовать пользователя"
    )
    @PostMapping("/auth")
    public ResponseEntity<String> auth(@RequestParam String email, @RequestParam String password) {
        try {
            String myToken = userService.authUser(email, password);
            return ResponseEntity.ok("User is authorized. Token is " + myToken);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user-info")
    public ResponseEntity<String> getUserInfoByToken(@RequestParam String token) {
        try {
            User user = userService.getUserByToken(token);
            return ResponseEntity.ok(user.toString());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
