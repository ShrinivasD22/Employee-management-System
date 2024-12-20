package com.example.employee.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.employee.Entity.Department;
import com.example.employee.Repository.DepartmentRepository;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(String id) {
        return departmentRepository.findById(id).orElse(null);
    }
}

