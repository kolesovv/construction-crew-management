package com.epam.constructioncrewmanagement.converter;

import com.epam.constructioncrewmanagement.dto.EmployeeDto;
import com.epam.constructioncrewmanagement.dto.PositionDto;
import com.epam.constructioncrewmanagement.dto.ProjectDto;
import com.epam.constructioncrewmanagement.dto.ProjectEmployeeDto;
import com.epam.constructioncrewmanagement.entity.Employee;
import com.epam.constructioncrewmanagement.entity.Position;
import com.epam.constructioncrewmanagement.entity.Project;
import com.epam.constructioncrewmanagement.entity.ProjectEmployee;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProjectEmployeeConverter {

  private ProjectConverter projectConverter;
  private EmployeeConverter employeeConverter;
  private PositionConverter positionConverter;

  public ProjectEmployeeDto mapToDto(ProjectEmployee projectEmployee) {

    Project project = projectEmployee.getProject();
    Employee employee = projectEmployee.getEmployee();
    Position position = projectEmployee.getPosition();

    ProjectDto projectDto = projectConverter.mapToDto(project);
    EmployeeDto employeeDto = employeeConverter.mapToDto(employee);
    PositionDto positionDto = positionConverter.mapToDto(position);

    return new ProjectEmployeeDto(
        projectEmployee.getId(),
        projectDto,
        employeeDto,
        positionDto,
        projectEmployee.getEntranceDate(),
        projectEmployee.getLeaveDate());
  }

  public ProjectEmployee mapToEntity(ProjectEmployeeDto projectEmployeeDto) {

    ProjectDto projectDto = projectEmployeeDto.getProjectDto();
    EmployeeDto employeeDto = projectEmployeeDto.getEmployeeDto();
    PositionDto positionDto = projectEmployeeDto.getPositionDto();

    Employee employee = employeeConverter.mapToEntity(employeeDto);
    Project project = projectConverter.mapToEntity(projectDto);
    Position position = positionConverter.mapToEntity(positionDto);

    return ProjectEmployee.builder()
        .id(projectEmployeeDto.getId())
        .employee(employee)
        .position(position)
        .project(project)
        .entranceDate(projectEmployeeDto.getEntranceDate())
        .leaveDate(projectEmployeeDto.getLeaveDate())
        .build();
  }
}
