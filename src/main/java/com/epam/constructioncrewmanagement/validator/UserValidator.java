package com.epam.constructioncrewmanagement.validator;

import com.epam.constructioncrewmanagement.entity.Employee;
import com.epam.constructioncrewmanagement.service.EmployeeService;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserValidator {

  EmployeeService employeeService;

  public String verifyPassword(String password, String matchingPassword) {

    String message = "";
    if (password != null && matchingPassword != null && !password.equals(matchingPassword)) {
      message = "Passwords don't match";
    }
    return message;
  }

  public String checkEmailExistence(String email) {

    String message = "";
    Optional<Employee> optionalEmployee = employeeService.findByEmail(email);
    if (optionalEmployee.isPresent()) {
      message = "An account for that username/email already exists";
    }
    return message;
  }
}
