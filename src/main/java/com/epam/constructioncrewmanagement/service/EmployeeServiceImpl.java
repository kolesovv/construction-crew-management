package com.epam.constructioncrewmanagement.service;

import com.epam.constructioncrewmanagement.entity.Employee;
import com.epam.constructioncrewmanagement.exception.EntityNotFoundException;
import com.epam.constructioncrewmanagement.repository.EmployeeRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

  private EmployeeRepository employeeRepository;

  @Override
  public void add(Employee employee) {

    employeeRepository.save(employee);
  }

  @Override
  public Employee getById(Long employeeId) {

    return employeeRepository.getById(employeeId);
  }

  @Override
  public List<Employee> getAll() {

    return employeeRepository.findAll();
  }

  @Override
  public void update(Employee employee) {

    if (!employeeRepository.existsById(employee.getId())) {
      throw new EntityNotFoundException("Employee with id " + employee.getId() + " does not exist");
    }
    employeeRepository.save(employee);
  }

  @Override
  public void delete(Long employeeId) {

    if (!employeeRepository.existsById(employeeId)) {
      throw new EntityNotFoundException("Employee with id " + employeeId + " does not exist");
    }
    employeeRepository.deleteById(employeeId);
  }

  @Override
  public Optional<Employee> findByEmail(String email) {

    return employeeRepository.findByEmail(email);
  }
}
