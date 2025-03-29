package org.example.springfsexam.Repository;


import org.example.springfsexam.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EmployeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartementId(Long departementId);
    List<Employee> findByAgeGreaterThan(int age);
}
