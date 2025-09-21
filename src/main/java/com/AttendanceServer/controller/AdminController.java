package com.AttendanceServer.controller;

import com.AttendanceServer.dto.UserDTO;
import com.AttendanceServer.service.AdminService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/create-user")
    public ResponseEntity<?> signupUser(@RequestBody UserDTO dto){
        try {
            UserDTO createUser = adminService.createUser(dto);
            return new ResponseEntity<>(createUser, HttpStatus.OK);
        }catch (EntityExistsException | EntityNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }catch (Exception e){
            return new ResponseEntity<>("User not Created, Come again latter", HttpStatus.NOT_ACCEPTABLE);
        }

    }
    @GetMapping("/managers")
    public ResponseEntity<?> getAllManagers(){
        try {
            return ResponseEntity.ok(adminService.getAllManagers());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping("/employees")
    public ResponseEntity<?> getAllEmployees(){
        try {
            return ResponseEntity.ok(adminService.getAllEmployees());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
