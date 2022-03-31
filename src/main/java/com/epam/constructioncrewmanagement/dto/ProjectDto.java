package com.epam.constructioncrewmanagement.dto;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {

  private Long id;
  @NotNull(message = "Name cannot be null")
  @Size(min = 4, max = 100, message = "Name must be between 4 and 100 characters")
  private String name;

  @NotNull(message = "Address cannot be null")
  @Size(min = 4, max = 100, message = "Address must be between 4 and 100 characters")
  private String address;

  @NotNull(message = "Date cannot be null")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate dateStart;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate dateFinish;
}
