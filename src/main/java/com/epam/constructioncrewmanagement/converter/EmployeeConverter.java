package com.epam.constructioncrewmanagement.converter;

import com.epam.constructioncrewmanagement.dto.EmployeeDto;
import com.epam.constructioncrewmanagement.entity.Employee;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class EmployeeConverter {

  public EmployeeDto mapToDto(Employee employee) {

    return new EmployeeDto(
        employee.getId(),
        employee.getFirstName(),
        employee.getLastName(),
        employee.getEmail(),
        employee.getNumberPhone(),
        employee.getRole(),
        employee.getPassword(),
        employee.getPassword());
  }

  public Employee mapToEntity(EmployeeDto employeeDto) {

    return Employee.builder()
        .id(employeeDto.getId())
        .firstName(employeeDto.getFirstName())
        .lastName(employeeDto.getLastName())
        .email(employeeDto.getEmail())
        .numberPhone(employeeDto.getNumberPhone())
        .role(employeeDto.getRole())
        .password(employeeDto.getPassword())
        .build();
  }

  public List<EmployeeDto> mapToDtos(List<Employee> employees) {

    return employees
        .stream()
        .map(this::mapToDto)
        .collect(Collectors.toList());
  }
}
