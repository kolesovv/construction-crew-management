package com.epam.constructioncrewmanagement.entity;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "projects")
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String address;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate dateStart;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate dateFinish;

  @OneToMany(mappedBy = "project")
  private List<ProjectEmployee> projectEmployees;
}
