package com.epam.constructioncrewmanagement.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.epam.constructioncrewmanagement.entity.Employee;
import com.epam.constructioncrewmanagement.entity.Position;
import com.epam.constructioncrewmanagement.entity.Project;
import com.epam.constructioncrewmanagement.entity.ProjectEmployee;
import com.epam.constructioncrewmanagement.repository.ProjectEmployeeRepository;
import com.epam.constructioncrewmanagement.repository.ProjectRepository;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
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
class ProjectEmployeeServiceImplTest {

  private final Long projectEmployeeId = 1L;
  private final Project project = Project.builder()
      .id(78L)
      .address("Lakhta Center")
      .address("Lakhtinskij Prospect 2")
      .dateStart(LocalDate.of(2012, Month.JANUARY, 1))
      .dateFinish(LocalDate.of(2019, Month.JANUARY, 1))
      .build();
  private final ProjectEmployee projectEmployee = ProjectEmployee.builder()
      .id(projectEmployeeId)
      .employee(Employee.builder()
          .build())
      .position(Position.builder()
          .build())
      .project(project)
      .entranceDate(LocalDate.of(2022, Month.JANUARY, 1))
      .build();

  @Captor
  ArgumentCaptor<ProjectEmployee> projectEmployeeArgumentCaptor;
  @InjectMocks
  private ProjectEmployeeServiceImpl projectEmployeeService;
  @Mock
  private ProjectEmployeeRepository projectEmployeeRepository;
  @Mock
  private ProjectRepository projectRepository;

  @Test
  void getAllActiveEmployeesByProjectId_activeEmployeesExist_projectEmployeesListIsReturned() {

    //GIVEN
    List<ProjectEmployee> projectEmployeeList = Collections.singletonList(projectEmployee);

    Mockito.when(projectRepository.getById(78L))
        .thenReturn(project);
    Mockito.when(projectEmployeeRepository.findProjectEmployeesByProjectAndLeaveDateNull(project))
        .thenReturn(projectEmployeeList);

    //WHEN
    List<ProjectEmployee> actual = projectEmployeeService.getAllActiveEmployeesByProjectId(project.getId());

    //THEN
    Mockito.verify(projectEmployeeRepository)
        .findProjectEmployeesByProjectAndLeaveDateNull(project);

    assertThat(actual).isEqualTo(projectEmployeeList);
  }

  @Test
  void getAllActiveEmployeesByProjectId_inactiveEmployeesExist_emptyProjectEmployeesListIsReturned() {

    //GIVEN
    projectEmployee.setLeaveDate(LocalDate.of(2022, Month.DECEMBER, 1));
    List<ProjectEmployee> projectEmployeeList = new ArrayList<>();

    Mockito.when(projectRepository.getById(78L))
        .thenReturn(project);
    Mockito.when(projectEmployeeRepository.findProjectEmployeesByProjectAndLeaveDateNull(project))
        .thenReturn(projectEmployeeList);

    //WHEN
    List<ProjectEmployee> actual = projectEmployeeService.getAllActiveEmployeesByProjectId(project.getId());

    //THEN
    Mockito.verify(projectEmployeeRepository)
        .findProjectEmployeesByProjectAndLeaveDateNull(project);

    assertThat(actual).isEqualTo(projectEmployeeList);
  }

  @Test
  void save_correctProjectEmployeeFormIsGiven_projectEmployeeIsCreated() {

    //WHEN
    projectEmployeeService.save(projectEmployee);

    //THEN
    Mockito.verify(projectEmployeeRepository)
        .save(projectEmployeeArgumentCaptor.capture());

    assertThat(projectEmployeeArgumentCaptor.getValue()).isEqualTo(projectEmployee);
  }

  @Test
  void removeEmployeeFromProject_activeEmployeeExist_employeeIsRemoved() {

    //GIVEN
    Mockito.when(projectEmployeeRepository.getById(projectEmployeeId))
        .thenReturn(projectEmployee);

    //WHEN
    projectEmployee.setLeaveDate(LocalDate.now());
    projectEmployeeService.removeEmployeeFromProject(projectEmployeeId);

    //THEN
    Mockito.verify(projectEmployeeRepository)
        .save(projectEmployeeArgumentCaptor.capture());

    assertThat(projectEmployeeArgumentCaptor.getValue()).isEqualTo(projectEmployee);
  }
}
