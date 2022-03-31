package com.epam.constructioncrewmanagement.controller;

import com.epam.constructioncrewmanagement.converter.EmployeeConverter;
import com.epam.constructioncrewmanagement.dto.EmployeeDto;
import com.epam.constructioncrewmanagement.entity.Employee;
import com.epam.constructioncrewmanagement.entity.Role;
import com.epam.constructioncrewmanagement.service.EmployeeService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/employees")
@AllArgsConstructor
public class EmployeeController {

  private final EmployeeService employeeService;
  private final EmployeeConverter employeeConverter;

  @GetMapping
  public String getEmployees(Model model) {

    List<EmployeeDto> employeeDtos = employeeService.getAll()
        .stream()
        .map(employeeConverter::mapToDto)
        .collect(Collectors.toList());
    model.addAttribute("employeeDtos", employeeDtos);
    return "employees";
  }

  @GetMapping("/add")
  public String getEmployeeAddForm(Model model) {

    Employee employee = new Employee();
    model.addAttribute("employee", employee);
    model.addAttribute("action", "add");
    model.addAttribute("roles", Role.values());
    return "employee-form";
  }

  @PostMapping("/save")
  public String addEmployee(@ModelAttribute("employee") EmployeeDto employeeDto) {

    employeeService.add(employeeConverter.mapToEntity(employeeDto));
    return "redirect:/employees";
  }

  @RequestMapping(value = "/{id}")
  public String getEmployeeUpdateForm(@PathVariable long id, Model model) {

    Employee employee = employeeService.getById(id);
    model.addAttribute("employee", employee);
    model.addAttribute("action", "update");
    model.addAttribute("roles", Role.values());
    return "employee-form";
  }

  @PostMapping("/update")
  public String updateEmployee(@ModelAttribute("employee") EmployeeDto employeeDto) {

    long id = employeeDto.getId();
    Employee employee = employeeService.getById(id);
    String bCryptPassword = employee.getPassword();
    employeeDto.setPassword(bCryptPassword);
    Employee updatedEmployee = employeeConverter.mapToEntity(employeeDto);
    employeeService.update(updatedEmployee);
    return "redirect:/employees";
  }
}
