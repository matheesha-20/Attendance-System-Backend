package com.AttendanceServer.service;

import com.AttendanceServer.dto.AttendanceDTO;
import com.AttendanceServer.dto.LeaveRequestDTO;
import com.AttendanceServer.entities.Attendance;
import com.AttendanceServer.entities.LeaveRequest;
import com.AttendanceServer.entities.Project;
import com.AttendanceServer.entities.User;
import com.AttendanceServer.enums.UserRole;
import com.AttendanceServer.repository.AttendanceRepository;
import com.AttendanceServer.repository.LeaveRequestRepository;
import com.AttendanceServer.repository.ProjectRepository;
import com.AttendanceServer.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    public AttendanceDTO markAttendance(AttendanceDTO dto){

        Optional<Attendance> optionalAttendance = attendanceRepository.findByEmployeeIdAndProjectIdAndDate(
                dto.getEmployeeId(), dto.getProjectId(), LocalDate.now()
        );

        if (optionalAttendance.isEmpty()) {
            Optional<User> optionalEmployee = userRepository.findById(dto.getEmployeeId());
            Optional<User> optionalManager = userRepository.findById(dto.getManagerId());
            Optional<Project> optionalProject = projectRepository.findById(dto.getProjectId());

            if (optionalEmployee.isPresent() && optionalManager.isPresent() && optionalProject.isPresent()) {
                Attendance attendance = new Attendance();

                attendance.setDate(LocalDate.now());
                attendance.setAttendanceStatus(dto.getAttendanceStatus());
                attendance.setEmployee(optionalEmployee.get());
                attendance.setProject(optionalProject.get());
                attendance.setManager(optionalManager.get());

                return attendanceRepository.save(attendance).getDto();
            } else {
                throw new EntityNotFoundException("Some Related Entity Not Found");
            }
        }
        else{
            throw new EntityExistsException("Attendance Already Marked For Today");
        }
    }
    public LeaveRequestDTO applyLeave(LeaveRequestDTO dto) {
        Optional<LeaveRequest> optionalLeaveRequest = leaveRequestRepository.findByEmployeeIdAndProjectIdAndDate(
                dto.getEmployeeId(), dto.getProjectId(), LocalDate.now()
        );

        if (optionalLeaveRequest.isEmpty()) {
            Optional<User> optionalEmployee = userRepository.findById(dto.getEmployeeId());
            Optional<User> optionalManager = userRepository.findByProjectIdAndUserRole(dto.getProjectId(), UserRole.MANAGER);
            Optional<Project> optionalProject = projectRepository.findById(dto.getProjectId());

            if (optionalEmployee.isPresent() && optionalManager.isPresent() && optionalProject.isPresent()) {
                LeaveRequest leaveRequest = new LeaveRequest();

                leaveRequest.setDate(LocalDate.now());
                leaveRequest.setEmployee(optionalEmployee.get());
                leaveRequest.setManager(optionalManager.get());
                leaveRequest.setProject(optionalProject.get());

                return leaveRequestRepository.save(leaveRequest).getDto();
            } else {
                throw new EntityNotFoundException("Some Related Entity Not Found");
            }
        } else {
            throw new EntityExistsException("Leave Already Applied For Today");
        }
    }

    public List<LeaveRequestDTO> getAllEmployeeLeaves(Long id){
        return leaveRequestRepository.findAllByEmployeeId(id).stream().map(LeaveRequest::getDto).collect(Collectors.toList());
    }
}
