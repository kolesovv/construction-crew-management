package com.epam.constructioncrewmanagement.repository;

import com.epam.constructioncrewmanagement.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
