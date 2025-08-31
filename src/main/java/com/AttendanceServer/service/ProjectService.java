package com.AttendanceServer.service;

import com.AttendanceServer.dto.ProjectDTO;
import com.AttendanceServer.entities.Project;
import com.AttendanceServer.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProjectService {


    @Autowired
    private ProjectRepository projectRepository;

    public ProjectDTO addProject(ProjectDTO dto){
        Project project = new Project();
        project.setName(dto.getName());
        project.setDuration(dto.getDuration());
        project.setStartDate(dto.getStartDate());

        return projectRepository.save(project).getDto();
    }

    public List<ProjectDTO> getAllProjects(){
        return projectRepository.findAll().stream().map(Project::getDto).collect(Collectors.toList());
    }
}
