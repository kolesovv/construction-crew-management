package com.epam.constructioncrewmanagement.service;

import com.epam.constructioncrewmanagement.entity.ProjectEmployee;
import java.util.List;

public interface ProjectEmployeeService {

  List<ProjectEmployee> getAllActiveEmployeesByProjectId(Long projectId);

  void save(ProjectEmployee projectEmployee);

  void removeEmployeeFromProject(Long projectEmployeeId);
}
