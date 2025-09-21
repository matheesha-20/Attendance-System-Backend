package com.AttendanceServer.controller;

import com.AttendanceServer.dto.UserDTO;
import com.AttendanceServer.entities.User;
import com.AttendanceServer.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO user){
        UserDTO dbUser = authService.login(user);

        if (dbUser == null){
            return new ResponseEntity<>("Wrong Conditional", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(dbUser, HttpStatus.OK);
    }

}
