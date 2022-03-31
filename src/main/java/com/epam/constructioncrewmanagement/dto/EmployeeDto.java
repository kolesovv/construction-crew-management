package com.epam.constructioncrewmanagement.dto;

import com.epam.constructioncrewmanagement.entity.Role;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

  private Long id;

  @NotEmpty(message = "Name cannot be empty")
  @NotBlank(message = "Name cannot be blank")
  private String firstName;

  @NotEmpty(message = "Name cannot be empty")
  @NotBlank(message = "Name cannot be blank")
  private String lastName;

  @NotEmpty(message = "Email cannot be empty")
  @NotBlank(message = "Email cannot be blank")
  private String email;

  @NotBlank(message = "Email cannot be blank")
  @Size(min = 11, max = 11, message = "The length of the phone number must be 11")
  private String numberPhone;

  private Role role;

  @NotEmpty(message = "Password cannot be empty")
  @NotBlank(message = "Password cannot be blank")
  @Size(min = 5, message = "Password length must be more than 5 characters")
  private String password;

  private String matchingPassword;
}
