package org.example.springfsexam.Repository;

import org.example.springfsexam.Entity.Departement;
import org.springframework.data.jpa.repository.JpaRepository;

// src/main/java/org/example/springfsexam/Repository/DepartmentRepository.java
public interface DepartementRepository extends JpaRepository<Departement, Long> {
}