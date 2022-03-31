package com.epam.constructioncrewmanagement.repository;

import com.epam.constructioncrewmanagement.entity.Project;
import com.epam.constructioncrewmanagement.entity.ProjectEmployee;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectEmployeeRepository extends JpaRepository<ProjectEmployee, Long> {

  List<ProjectEmployee> findProjectEmployeesByProjectAndLeaveDateNull(Project project);
}
