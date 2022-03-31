package com.epam.constructioncrewmanagement.service;

import com.epam.constructioncrewmanagement.entity.Project;
import com.epam.constructioncrewmanagement.entity.ProjectEmployee;
import com.epam.constructioncrewmanagement.repository.ProjectEmployeeRepository;
import com.epam.constructioncrewmanagement.repository.ProjectRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ProjectEmployeeServiceImpl implements ProjectEmployeeService {

  private ProjectEmployeeRepository projectEmployeeRepository;
  private ProjectRepository projectRepository;

  @Override
  public List<ProjectEmployee> getAllActiveEmployeesByProjectId(Long projectId) {

    Project project = projectRepository.getById(projectId);
    return projectEmployeeRepository.findProjectEmployeesByProjectAndLeaveDateNull(project);
  }

  @Override
  public void save(ProjectEmployee projectEmployee) {

    projectEmployeeRepository.save(projectEmployee);
  }

  @Override
  public void removeEmployeeFromProject(Long projectEmployeeId) {

    ProjectEmployee projectEmployee = projectEmployeeRepository.getById(projectEmployeeId);
    projectEmployee.setLeaveDate(LocalDate.now());
    save(projectEmployee);
  }
}
