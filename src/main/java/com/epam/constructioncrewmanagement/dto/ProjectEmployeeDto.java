package com.epam.constructioncrewmanagement.dto;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectEmployeeDto {

  private Long id;
  private ProjectDto projectDto;
  private EmployeeDto employeeDto;
  private PositionDto positionDto;

  @NotNull(message = "Date cannot be null")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate entranceDate;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate leaveDate;
}
