package org.example.springfsexam.Conroller;

import java.util.List;

import org.example.springfsexam.Entity.Departement;
import org.example.springfsexam.Repository.DepartementRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// src/main/java/org/example/springfsexam/Controller/DepartmentController.java
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/departements")
public class DepartmentController {

    private final DepartementRepository departmentRepository;

    public DepartmentController(DepartementRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @GetMapping
    public ResponseEntity<List<Departement>> getAllDepartements() {
        return ResponseEntity.ok(departmentRepository.findAll());
    }
}