package org.example.springfsexam.Conroller;

import org.example.springfsexam.Entity.Departement;
import org.example.springfsexam.Entity.Employee;
import org.example.springfsexam.Repository.DepartementRepository;
import org.example.springfsexam.Service.EmployeService;
import org.example.springfsexam.Service.FileStorageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/employes")
public class EmployeController {
    private final String uploadDir = "/Users/abc/SpringFsExam/src/main/java/org/example/springfsexam/uploads/";
    FileStorageService fileStorageService;
    private final EmployeService employeService;
    DepartementRepository departementRepository;
    public EmployeController(EmployeService employeService, DepartementRepository departementRepository, FileStorageService fileStorageService) {
        this.employeService = employeService;
        this.departementRepository = departementRepository;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/departement/{id}")
    public List<Employee> getEmployesByDepartement(@PathVariable Long id) {
        return employeService.getEmployesByDepartement(id);
    }

    // Add this new endpoint to serve images
    @GetMapping("/uploads/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) throws IOException {
        Path imagePath = Paths.get(uploadDir + filename);
        if (!Files.exists(imagePath)) {
            return ResponseEntity.notFound().build();
        }
        byte[] imageBytes = Files.readAllBytes(imagePath);
        return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_JPEG) // or detect media type dynamically
            .body(imageBytes);
    }

    // Update the addEmploye method
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Employee> addEmploye(

        @RequestPart("employe") String employeJson,
        @RequestPart(value = "photo", required = false) MultipartFile photo,
        @RequestParam Long departmentId) throws IOException {
        System.out.println("yaharo");
        ObjectMapper objectMapper = new ObjectMapper();
        Employee employe = objectMapper.readValue(employeJson, Employee.class);

        Departement department = departementRepository.findById(departmentId)
            .orElseThrow(() -> new RuntimeException("Department not found"));
        employe.setDepartement(department);

        // Remove the file handling from here
        Employee savedEmploye = employeService.addEmploye(employe, photo);
        return ResponseEntity.ok(savedEmploye);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmploye(@PathVariable Long id) {
        employeService.deleteEmploye(id);
        return ResponseEntity.ok("Employé et photo supprimés !");
    }

    @DeleteMapping("/age/60")
    public ResponseEntity<?> deleteEmployesOver60() {
        employeService.deleteEmployesOver60();
        return ResponseEntity.ok("Employés de plus de 60 ans supprimés !");
    }
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Employee> updateEmploye(
        @PathVariable Long id,
        @RequestPart("employe") String employeJson,
        @RequestPart(value = "photo", required = false) MultipartFile photo,
        @RequestParam String departmentId) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        Employee employe = objectMapper.readValue(employeJson, Employee.class);

        Departement department = departementRepository.findById(Long.valueOf(departmentId))
            .orElseThrow(() -> new RuntimeException("Department not found"));
        employe.setDepartement(department);

        Employee updatedEmploye = employeService.updateEmploye(id, employe, photo);
        return ResponseEntity.ok(updatedEmploye);
    }

    // Add this method as well to fetch a single employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeById(@PathVariable Long id) {
        Employee employe = employeService.getEmployeById(id);
        if (employe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employe);
    }
}
