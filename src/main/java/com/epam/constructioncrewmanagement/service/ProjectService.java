package com.epam.constructioncrewmanagement.service;

import com.epam.constructioncrewmanagement.entity.Project;
import java.util.List;

public interface ProjectService {

  void add(Project project);

  Project getById(Long projectId);

  List<Project> getAll();

  void update(Project project);

  void delete(Long projectId);
}
