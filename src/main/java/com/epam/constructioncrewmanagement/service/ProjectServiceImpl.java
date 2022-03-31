package com.epam.constructioncrewmanagement.service;

import com.epam.constructioncrewmanagement.entity.Project;
import com.epam.constructioncrewmanagement.exception.EntityNotFoundException;
import com.epam.constructioncrewmanagement.repository.ProjectRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {

  private ProjectRepository projectRepository;

  @Override
  public void add(Project project) {

    projectRepository.save(project);
  }

  @Override
  public Project getById(Long projectId) {

    return projectRepository.getById(projectId);
  }

  @Override
  public List<Project> getAll() {

    return projectRepository.findAll();
  }

  @Override
  public void update(Project project) {

    if (!projectRepository.existsById(project.getId())) {
      throw new EntityNotFoundException("Project with id " + project.getId() + " does not exist");
    }
    projectRepository.save(project);
  }

  @Override
  public void delete(Long projectId) {

    if (!projectRepository.existsById(projectId)) {
      throw new EntityNotFoundException("Project with id " + projectId + " does not exist");
    }
    projectRepository.deleteById(projectId);
  }
}
