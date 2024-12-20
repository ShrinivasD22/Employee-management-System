package com.example.employee.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.employee.Entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {
}

