package org.example.springfsexam.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String email;
    private LocalDate dateNaissance;
    private int age;

    @ManyToOne
    @JoinColumn(name = "departement_id")
    private Departement departement;

    private String photo; // Chemin du fichier sur le disque

    // Constructors
    public Employee() {}

    public Employee(Long id, String nom, String email, LocalDate dateNaissance, int age, Departement departement, String photo) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.dateNaissance = dateNaissance;
        this.age = age;
        this.departement = departement;
        this.photo = photo;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
