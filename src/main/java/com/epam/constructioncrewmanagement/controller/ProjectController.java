package com.epam.constructioncrewmanagement.controller;

import com.epam.constructioncrewmanagement.converter.EmployeeConverter;
import com.epam.constructioncrewmanagement.converter.PositionConverter;
import com.epam.constructioncrewmanagement.converter.ProjectConverter;
import com.epam.constructioncrewmanagement.converter.ProjectEmployeeConverter;
import com.epam.constructioncrewmanagement.dto.EmployeeDto;
import com.epam.constructioncrewmanagement.dto.PositionDto;
import com.epam.constructioncrewmanagement.dto.ProjectDto;
import com.epam.constructioncrewmanagement.dto.ProjectEmployeeDto;
import com.epam.constructioncrewmanagement.entity.Employee;
import com.epam.constructioncrewmanagement.entity.Position;
import com.epam.constructioncrewmanagement.entity.Project;
import com.epam.constructioncrewmanagement.entity.ProjectEmployee;
import com.epam.constructioncrewmanagement.service.EmployeeService;
import com.epam.constructioncrewmanagement.service.PositionService;
import com.epam.constructioncrewmanagement.service.ProjectEmployeeService;
import com.epam.constructioncrewmanagement.service.ProjectService;
import com.epam.constructioncrewmanagement.validator.ProjectEmployeeValidator;
import com.epam.constructioncrewmanagement.validator.ProjectValidator;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/projects")
@AllArgsConstructor
public class ProjectController {

  private final EmployeeService employeeService;
  private final PositionService positionService;
  private final ProjectService projectService;
  private final ProjectEmployeeService projectEmployeeService;
  private final EmployeeConverter employeeConverter;
  private final PositionConverter positionConverter;
  private final ProjectConverter projectConverter;
  private final ProjectEmployeeConverter projectEmployeeConverter;
  private final ProjectValidator projectValidator;
  private final ProjectEmployeeValidator projectEmployeeValidator;

  @GetMapping
  public String getProjects(Model model) {

    List<ProjectDto> projectDtos = projectService.getAll()
        .stream()
        .map(projectConverter::mapToDto)
        .collect(Collectors.toList());
    model.addAttribute("projectDtos", projectDtos);
    return "projects";
  }

  @GetMapping(value = "/{id}")
  public String getProject(@PathVariable long id, Model model) {

    Project project = projectService.getById(id);
    ProjectDto projectDto = projectConverter.mapToDto(project);
    List<ProjectEmployee> projectEmployees =
        projectEmployeeService.getAllActiveEmployeesByProjectId(id);
    List<ProjectEmployeeDto> projectEmployeeDtos = projectEmployees
        .stream()
        .map(projectEmployeeConverter::mapToDto)
        .collect(Collectors.toList());
    model.addAttribute("projectDto", projectDto);
    model.addAttribute("projectEmployeesDtos", projectEmployeeDtos);
    return "project-profile";
  }

  @GetMapping("/{id}/add")
  public String addEmployeeToProject(@PathVariable long id, Model model) {

    List<Employee> employees = employeeService.getAll();
    List<Position> positions = positionService.getAll();
    List<EmployeeDto> employeeDtos = employeeConverter.mapToDtos(employees);
    List<PositionDto> positionDtos = positionConverter.mapToDtos(positions);
    Project project = projectService.getById(id);
    ProjectDto projectDto = projectConverter.mapToDto(project);
    ProjectEmployeeDto projectEmployeeDto = ProjectEmployeeDto.builder()
        .id(0L)
        .projectDto(projectDto)
        .build();
    model.addAttribute("projectEmployeeDto", projectEmployeeDto);
    model.addAttribute("employeeDtos", employeeDtos);
    model.addAttribute("positionDtos", positionDtos);
    model.addAttribute("projectDto", projectDto);
    return "assign-employee";
  }

  @PostMapping("/assign")
  public String assignEmployee(@Valid ProjectEmployeeDto projectEmployeeDto,
      BindingResult bindingResult, Model model) {

    ProjectDto projectDto = projectEmployeeDto.getProjectDto();
    long projectId = projectDto.getId();
    Project project = projectService.getById(projectId);
    LocalDate startDateProject = project.getDateStart();
    LocalDate endDateProject = project.getDateFinish();
    LocalDate entranceEmployeeDate = projectEmployeeDto.getEntranceDate();
    bindingResult = projectEmployeeValidator.
        validateDates(bindingResult, startDateProject, endDateProject, entranceEmployeeDate);
    if (bindingResult.hasErrors()) {
      List<Employee> employees = employeeService.getAll();
      List<Position> positions = positionService.getAll();
      List<EmployeeDto> employeeDtos = employeeConverter.mapToDtos(employees);
      List<PositionDto> positionDtos = positionConverter.mapToDtos(positions);
      projectDto = projectConverter.mapToDto(project);
      model.addAttribute("projectEmployeeDto", projectEmployeeDto);
      model.addAttribute("employeeDtos", employeeDtos);
      model.addAttribute("positionDtos", positionDtos);
      model.addAttribute("projectDto", projectDto);
      return "assign-employee";
    }

    EmployeeDto employeeDto = projectEmployeeDto.getEmployeeDto();
    PositionDto positionDto = projectEmployeeDto.getPositionDto();
    long employeeId = employeeDto.getId();
    long positionId = positionDto.getId();
    Employee employee = employeeService.getById(employeeId);
    Position position = positionService.getById(positionId);
    LocalDate entranceDate = projectEmployeeDto.getEntranceDate();
    ProjectEmployee projectEmployee =
        ProjectEmployee.builder()
            .employee(employee)
            .position(position)
            .project(project)
            .entranceDate(entranceDate)
            .build();
    projectEmployeeService.save(projectEmployee);
    return String.format("redirect:/projects/%d", projectId);
  }

  @GetMapping(value = "/{id}/update")
  public String getProjectUpdateForm(@PathVariable long id, Model model) {

    Project project = projectService.getById(id);
    model.addAttribute("projectDto", projectConverter.mapToDto(project));
    return "project-form";
  }

  @PostMapping("/update")
  public String updateProject(@Valid ProjectDto projectDto, BindingResult bindingResult) {

    String err = projectValidator.validateDates(projectDto.getDateStart(), projectDto.getDateFinish());
    if (!err.isEmpty()) {
      ObjectError error = new ObjectError("error", err);
      bindingResult.addError(error);
    }
    if (bindingResult.hasErrors()) {
      return "project-form";
    }

    Project project = projectConverter.mapToEntity(projectDto);
    projectService.update(project);
    long projectId = project.getId();
    return String.format("redirect:/projects/%d", projectId);
  }

  @PostMapping("/{projectId}/employee/{id}")
  public String removeEmployeeFromProject(@PathVariable long projectId, @PathVariable long id) {

    projectEmployeeService.removeEmployeeFromProject(id);
    return String.format("redirect:/projects/%d", projectId);
  }

  @GetMapping("/add")
  public String getProjectAddForm(Model model) {

    ProjectDto projectDto = projectConverter.mapToDto(new Project());
    model.addAttribute("projectDto", projectDto);
    model.addAttribute("action", "add");
    return "project-form";
  }

  @PostMapping("/save")
  public String addProject(@ModelAttribute ProjectDto projectDto) {

    Project project = projectConverter.mapToEntity(projectDto);
    projectService.add(project);
    return "redirect:/projects";
  }
}
