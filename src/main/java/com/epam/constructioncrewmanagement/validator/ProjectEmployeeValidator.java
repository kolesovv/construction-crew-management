package com.epam.constructioncrewmanagement.validator;

import java.time.LocalDate;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

@Component
public class ProjectEmployeeValidator {

  private String validateEntranceDateWithStartDate(LocalDate startDateProject, LocalDate entranceEmployeeDate) {

    String message = "";
    if (startDateProject != null && entranceEmployeeDate != null && !entranceEmployeeDate.isAfter(startDateProject)) {
      message = "The employee's entry date must be greater than or equal to the project start date";
    }
    return message;
  }

  private String validateEntranceDateWithEndDate(LocalDate endDateProject, LocalDate entranceEmployeeDate) {

    String message = "";
    if (endDateProject != null && entranceEmployeeDate != null && !entranceEmployeeDate.isBefore(endDateProject)) {
      message = "The employee's entry date must be less than or equal to the project end date";
    }
    return message;
  }

  public BindingResult validateDates(BindingResult bindingResult, LocalDate startDateProject, LocalDate endDateProject,
      LocalDate entranceEmployeeDate) {

    String errorMessageEntranceDateWithStartDate =
        validateEntranceDateWithStartDate(startDateProject, entranceEmployeeDate);
    String errorMessageEntranceDateWithEndDate =
        validateEntranceDateWithEndDate(endDateProject, entranceEmployeeDate);
    if (!errorMessageEntranceDateWithStartDate.isEmpty()) {
      ObjectError entranceDateWithStartDateNotValidError =
          new ObjectError("errorMessageEntranceDateWithStartDate", errorMessageEntranceDateWithStartDate);
      bindingResult.addError(entranceDateWithStartDateNotValidError);
    }
    if (!errorMessageEntranceDateWithEndDate.isEmpty()) {
      ObjectError entranceDateWithEndDateNotValidError =
          new ObjectError("errorMessageEntranceDateWithEndDate", errorMessageEntranceDateWithEndDate);
      bindingResult.addError(entranceDateWithEndDateNotValidError);
    }
    return bindingResult;
  }
}
