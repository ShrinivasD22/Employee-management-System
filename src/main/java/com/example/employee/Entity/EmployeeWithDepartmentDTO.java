package com.example.employee.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data

@NoArgsConstructor
public class EmployeeWithDepartmentDTO {
    private String id;
    private String name;
    private String email;
    private String position;
    private double salary;
    private String departmentId; // department ID
    
    public EmployeeWithDepartmentDTO(String id, String name, String email, String position, double salary, String departmentId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.position = position;
        this.salary = salary;
        this.departmentId = departmentId;
    }

}

