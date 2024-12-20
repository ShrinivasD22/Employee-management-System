package com.example.employee.Entity;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Department {
    @Id
    private String id;
    private String name;
    private String location;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Employee> employees;
}

