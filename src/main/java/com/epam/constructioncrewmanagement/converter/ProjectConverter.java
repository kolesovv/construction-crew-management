package com.epam.constructioncrewmanagement.converter;

import com.epam.constructioncrewmanagement.dto.ProjectDto;
import com.epam.constructioncrewmanagement.entity.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectConverter {

  public ProjectDto mapToDto(Project project) {

    return new ProjectDto(
        project.getId(),
        project.getName(),
        project.getAddress(),
        project.getDateStart(),
        project.getDateFinish());
  }

  public Project mapToEntity(ProjectDto projectDto) {

    return Project.builder()
        .id(projectDto.getId())
        .name(projectDto.getName())
        .address(projectDto.getAddress())
        .dateStart(projectDto.getDateStart())
        .dateFinish(projectDto.getDateFinish())
        .build();
  }
}
