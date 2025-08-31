package com.AttendanceServer.dto;

import com.AttendanceServer.entities.Project;
import com.AttendanceServer.entities.User;
import com.AttendanceServer.enums.AttendanceStatus;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AttendanceDTO {

    private Long id;

    private LocalDate date;

    private AttendanceStatus attendanceStatus;

    private Long projectId;
    private String projectName;

    private Long employeeId;
    private String employeeName;

    private Long managerId;
    private String managerName;
}
