package com.epam.constructioncrewmanagement.validator;

import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class ProjectValidator {

  public String validateDates(LocalDate start, LocalDate finish) {

    String message = "";
    if (start != null && finish != null && start.isAfter(finish)) {
      message = "Finish date should be greater than start date";
    }
    return message;
  }
}
