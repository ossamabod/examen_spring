package org.example.springfsexam.Service;


import org.example.springfsexam.Entity.Employee;
import org.example.springfsexam.Repository.EmployeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import jakarta.transaction.Transactional;

@Service
public class EmployeService {
    private final EmployeRepository employeRepository;
    private final FileStorageService fileStorageService;

    public EmployeService(EmployeRepository employeRepository, FileStorageService fileStorageService) {
        this.employeRepository = employeRepository;
        this.fileStorageService = fileStorageService;
    }

    public List<Employee> getEmployesByDepartement(Long departementId) {
        return employeRepository.findByDepartementId(departementId);
    }

    public Employee addEmploye(Employee employe, MultipartFile photo) throws IOException {
        if (photo != null && !photo.isEmpty()) {
            String photoPath = fileStorageService.saveFile(photo);
            employe.setPhoto(photoPath);
        }
        return employeRepository.save(employe);
    }

    public void deleteEmploye(Long id) {
        Employee employee = employeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Employee not found"));


        if (employee.getPhoto() != null && !employee.getPhoto().isEmpty()) {
            System.out.println("pgoto"+employee.getPhoto());
            fileStorageService.deleteFile(employee.getPhoto());
        }

        employeRepository.deleteById(id);
    }

    @Transactional
    public void deleteEmployesOver60() {
        // Correct approach:
        // 1. First find the employees
        List<Employee> employees = employeRepository.findByAgeGreaterThan(60);

        // 2. Delete files
        employees.forEach(emp -> fileStorageService.deleteFile(emp.getPhoto()));

        // 3. Then delete entities
        employeRepository.deleteAll(employees);
    }
    public Employee getEmployeById(Long id) {
        return employeRepository.findById(id).orElse(null);
    }

    public Employee updateEmploye(Long id, Employee updatedEmploye, MultipartFile newPhoto) throws IOException {
        Employee existingEmploye = employeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Update basic fields
        existingEmploye.setNom(updatedEmploye.getNom());
        existingEmploye.setAge(updatedEmploye.getAge());

        // Handle photo update
        if (newPhoto != null && !newPhoto.isEmpty()) {
            // Delete old photo
            if (existingEmploye.getPhoto() != null) {
                fileStorageService.deleteFile(existingEmploye.getPhoto());
            }
            // Save new photo
            String newFileName = fileStorageService.saveFile(newPhoto);
            existingEmploye.setPhoto(newFileName);
        }

        return employeRepository.save(existingEmploye);
    }
}
