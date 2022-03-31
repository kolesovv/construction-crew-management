package com.epam.constructioncrewmanagement.repository;

import com.epam.constructioncrewmanagement.entity.Employee;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  Optional<Employee> findByEmail(String email);
}
