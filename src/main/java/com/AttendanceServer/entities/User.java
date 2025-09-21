package com.AttendanceServer.entities;

import com.AttendanceServer.dto.UserDTO;
import com.AttendanceServer.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;

    private String password;

    private String name;

    private UserRole userRole;


    @ManyToOne
    private Project project;



    public UserDTO getDto(){
        UserDTO dto =  new UserDTO();

        dto.setId(id);
        dto.setName(name);
        dto.setUserRole(userRole);
        dto.setEmail(email);

        if(project != null){
          dto.setProjectId(project.getId());
          dto.setName(project.getName());
        }

        return dto;

    }
}
