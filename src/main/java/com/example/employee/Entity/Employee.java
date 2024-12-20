package com.example.employee.Entity;



import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employee {
    @Id
    private String id;
    private String name;
    private String email;
    private String position;
    private double salary;

    @ManyToOne
    @JoinColumn(name = "department_id")
    @JsonBackReference
    private Department department;
    
    @Transient
    public String getDepartmentId() {
        return department != null ? department.getId() : null;
    }
}

