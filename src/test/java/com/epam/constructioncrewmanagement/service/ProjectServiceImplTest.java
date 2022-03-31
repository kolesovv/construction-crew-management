package com.epam.constructioncrewmanagement.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import com.epam.constructioncrewmanagement.entity.Project;
import com.epam.constructioncrewmanagement.exception.EntityNotFoundException;
import com.epam.constructioncrewmanagement.repository.ProjectRepository;
import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

  private final Long projectId = 78L;
  private final Project project = Project.builder()
      .id(projectId)
      .address("Lakhta Center")
      .address("Lakhtinskij Prospect 2")
      .dateStart(LocalDate.of(2012, Month.JANUARY, 1))
      .dateFinish(LocalDate.of(2019, Month.JANUARY, 1))
      .build();
  @Captor
  ArgumentCaptor<Project> projectArgumentCaptor;
  @InjectMocks
  private ProjectServiceImpl projectService;
  @Mock
  private ProjectRepository projectRepository;

  @Test
  void add_correctProjectFormIsGiven_projectIsCreated() {

    //WHEN
    projectService.add(project);

    //THEN
    Mockito.verify(projectRepository)
        .save(projectArgumentCaptor.capture());

    assertThat(projectArgumentCaptor.getValue()).isEqualTo(project);
  }

  @Test
  void getById_projectIdIsGiven_projectIsReturned() {

    //GIVEN
    Mockito.when(projectRepository.getById(projectId))
        .thenReturn(project);

    //WHEN
    Project returnedProject = projectService.getById(projectId);

    //THEN
    assertThat(returnedProject).isEqualTo(project);
  }

  @Test
  void getAll_projectsExist_projectListIsReturned() {

    //GIVEN
    List<Project> projects = Collections.singletonList(project);
    Mockito.when(projectRepository.findAll())
        .thenReturn(projects);

    //WHEN
    List<Project> actual = projectService.getAll();

    //THEN
    Mockito.verify(projectRepository)
        .findAll();

    assertThat(actual).isEqualTo(projects);
  }

  @Test
  void update_projectExists_projectIsUpdated() {

    //GIVEN
    Mockito.when(projectRepository.existsById(projectId))
        .thenReturn(true);
    Project updatedProject = project;
    updatedProject.setDateFinish(LocalDate.of(2025, Month.JANUARY, 1));

    //WHEN
    projectService.update(updatedProject);

    //THEN
    Mockito.verify(projectRepository)
        .save(updatedProject);
  }

  @Test
  void update_projectDoesNotExist_projectNotFoundExceptionIsThrown() {

    //GIVEN
    Mockito.when(projectRepository.existsById(projectId))
        .thenReturn(false);

    //WHEN
    RuntimeException thrown = (RuntimeException) catchThrowable(() -> projectService.update(project));

    //THEN
    assertThat(thrown).isInstanceOf(EntityNotFoundException.class);
    assertThat(thrown.getMessage()).isEqualTo("Project with id " + projectId + " does not exist");
  }

  @Test
  void delete_projectExists_projectIsDeleted() {

    //GIVEN
    Mockito.when(projectRepository.existsById(projectId))
        .thenReturn(true);

    //WHEN
    projectService.delete(projectId);

    //THEN
    Mockito.verify(projectRepository)
        .deleteById(projectId);
  }

  @Test
  void delete_projectDoesNotExist_projectNotFoundExceptionIsThrown() {

    //GIVEN
    Long projectId = 0L;
    Mockito.when(projectRepository.existsById(projectId))
        .thenReturn(false);

    //WHEN
    RuntimeException thrown = (RuntimeException) catchThrowable(() -> projectService.delete(projectId));

    //THEN
    assertThat(thrown).isInstanceOf(EntityNotFoundException.class);
    assertThat(thrown.getMessage()).isEqualTo("Project with id " + projectId + " does not exist");
  }
}
