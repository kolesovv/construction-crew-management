package com.epam.constructioncrewmanagement.service;

import com.epam.constructioncrewmanagement.dto.EmployeeDetails;
import com.epam.constructioncrewmanagement.entity.Employee;
import com.epam.constructioncrewmanagement.repository.EmployeeRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EmployeeDetailsService implements UserDetailsService {

  private EmployeeRepository employeeRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    Optional<Employee> optionalEmployee = employeeRepository.findByEmail(email);
    if (optionalEmployee.isEmpty()) {
      throw new UsernameNotFoundException(String.format("Employee %s not found", email));
    }
    else {
      Employee employee = optionalEmployee.get();
      return new EmployeeDetails(
          employee.getEmail(),
          employee.getPassword(),
          employee.getRole()
      );
    }
  }
}
