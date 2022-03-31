package com.epam.constructioncrewmanagement.controller;

import com.epam.constructioncrewmanagement.converter.EmployeeConverter;
import com.epam.constructioncrewmanagement.dto.EmployeeDto;
import com.epam.constructioncrewmanagement.entity.Employee;
import com.epam.constructioncrewmanagement.entity.Role;
import com.epam.constructioncrewmanagement.service.EmployeeService;
import com.epam.constructioncrewmanagement.validator.UserValidator;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class LoginPageController {

  private PasswordEncoder passwordEncoder;
  private EmployeeService employeeService;
  private EmployeeConverter employeeConverter;
  private UserValidator userValidator;

  @GetMapping("/login")
  public String getLoginForm() {

    return "login";
  }

  @GetMapping("/registration")
  public String getRegistrationForm(Model model) {

    EmployeeDto employeeDto = new EmployeeDto();
    model.addAttribute("employeeDto", employeeDto);
    return "registration-form";
  }

  @PostMapping("/create")
  public String createNewUser(@Valid EmployeeDto employeeDto, BindingResult bindingResult) {

    String password = employeeDto.getPassword();
    String passwordMatch = employeeDto.getMatchingPassword();
    String email = employeeDto.getEmail();

    String errorMessagePasswordValidation = userValidator.verifyPassword(password, passwordMatch);
    String errorMessageEmailExistence = userValidator.checkEmailExistence(email);

    if (!errorMessagePasswordValidation.isEmpty()) {
      ObjectError passwordsMatchError = new ObjectError("passwordsMatchError", errorMessagePasswordValidation);
      bindingResult.addError(passwordsMatchError);
    }
    if (!errorMessageEmailExistence.isEmpty()) {
      ObjectError emailExistenceError = new ObjectError("emailExistenceError", errorMessageEmailExistence);
      bindingResult.addError(emailExistenceError);
    }
    if (bindingResult.hasErrors()) {
      return "registration-form";
    }

    Employee employee = employeeConverter.mapToEntity(employeeDto);
    String encodedPassword = passwordEncoder.encode(password);
    employee.setRole(Role.USER);
    employee.setPassword(encodedPassword);
    employeeService.add(employee);
    return "redirect:/login";
  }
}
