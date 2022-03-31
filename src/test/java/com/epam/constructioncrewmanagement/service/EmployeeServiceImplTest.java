package com.epam.constructioncrewmanagement.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import com.epam.constructioncrewmanagement.entity.Employee;
import com.epam.constructioncrewmanagement.exception.EntityNotFoundException;
import com.epam.constructioncrewmanagement.repository.EmployeeRepository;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

  private final Long employeeId = 1L;
  private final Employee employee = Employee.builder()
      .id(employeeId)
      .firstName("Antoni")
      .lastName("Gaudi")
      .email("antoni_gaudi@epam.com")
      .numberPhone("+34 900 000 000")
      .build();
  @Captor
  ArgumentCaptor<Employee> employeeArgumentCaptor;
  @InjectMocks
  private EmployeeServiceImpl employeeService;
  @Mock
  private EmployeeRepository employeeRepository;

  @Test
  void add_correctEmployeeFormIsGiven_employeeIsCreated() {

    //WHEN
    employeeService.add(employee);

    //THEN
    Mockito.verify(employeeRepository)
        .save(employeeArgumentCaptor.capture());

    assertThat(employeeArgumentCaptor.getValue()).isEqualTo(employee);
  }

  @Test
  void getById_employeeIdIsGiven_employeeIsReturned() {

    //GIVEN
    Mockito.when(employeeRepository.getById(employeeId))
        .thenReturn(employee);

    //WHEN
    Employee returnedEmployee = employeeService.getById(employeeId);

    //THEN
    assertThat(returnedEmployee).isEqualTo(employee);
  }

  @Test
  void getAll_employeesExist_employeesListIsReturned() {

    //GIVEN
    List<Employee> employees = Collections.singletonList(employee);
    Mockito.when(employeeRepository.findAll())
        .thenReturn(employees);

    //WHEN
    List<Employee> actual = employeeService.getAll();

    //THEN
    Mockito.verify(employeeRepository)
        .findAll();

    assertThat(actual).isEqualTo(employees);
  }

  @Test
  void update_projectExists_projectIsUpdated() {

    //GIVEN
    Mockito.when(employeeRepository.existsById(employeeId))
        .thenReturn(true);
    Employee updatedEmployee = employee;
    updatedEmployee.setEmail("antoni_gaudi@epam.es");

    //WHEN
    employeeService.update(updatedEmployee);

    //THEN
    Mockito.verify(employeeRepository)
        .save(updatedEmployee);
  }

  @Test
  void update_projectDoesNotExist_employeeNotFoundExceptionIsThrown() {

    //GIVEN
    Mockito.when(employeeRepository.existsById(employeeId))
        .thenReturn(false);

    //WHEN
    RuntimeException thrown = (RuntimeException) catchThrowable(() -> employeeService.update(employee));

    //THEN
    assertThat(thrown).isInstanceOf(EntityNotFoundException.class);
    assertThat(thrown.getMessage()).isEqualTo("Employee with id " + employeeId + " does not exist");
  }

  @Test
  void delete_employeeExists_employeeIsDeleted() {

    //GIVEN
    Mockito.when(employeeRepository.existsById(employeeId))
        .thenReturn(true);

    //WHEN
    employeeService.delete(employeeId);

    //THEN
    Mockito.verify(employeeRepository)
        .deleteById(employeeId);
  }

  @Test
  void delete_employeeDoesNotExists_employeeNotFoundExceptionIsThrown() {

    //GIVEN
    Long employeeId = 34L;
    Mockito.when(employeeRepository.existsById(employeeId))
        .thenReturn(false);

    //WHEN
    RuntimeException thrown = (RuntimeException) catchThrowable(() -> employeeService.delete(employeeId));

    //THEN
    assertThat(thrown).isInstanceOf(EntityNotFoundException.class);
    assertThat(thrown.getMessage()).isEqualTo("Employee with id " + employeeId + " does not exist");
  }
}
