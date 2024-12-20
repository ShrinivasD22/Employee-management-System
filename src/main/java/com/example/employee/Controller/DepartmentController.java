package com.example.employee.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.employee.Entity.Department;
import com.example.employee.Service.DepartmentService;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    // b. Get all departments
    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    // Get a department by ID
    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable String id) {
        return departmentService.getDepartmentById(id);
    }
}
