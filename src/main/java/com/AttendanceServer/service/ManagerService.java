package com.AttendanceServer.service;

import com.AttendanceServer.dto.UserDTO;
import com.AttendanceServer.entities.User;
import com.AttendanceServer.enums.UserRole;
import com.AttendanceServer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> getAllEmployeesByProject(Long projectId){
        List<User> users = userRepository.findAllByProjectIdAndUserRole(projectId, UserRole.EMPLOYEE);
        return users.stream().map(User::getDto).collect(Collectors.toList());
    }
}
