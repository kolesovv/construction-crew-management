package com.epam.constructioncrewmanagement.service;

import com.epam.constructioncrewmanagement.entity.Employee;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {

  void add(Employee employee);

  Employee getById(Long employeeId);

  List<Employee> getAll();

  void update(Employee employee);

  void delete(Long employeeId);

  Optional<Employee> findByEmail(String email);
}
