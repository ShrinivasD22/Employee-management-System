package com.example.employee.Controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.employee.Entity.Department;
import com.example.employee.Entity.Employee;
import com.example.employee.Repository.DepartmentRepository;

import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public void run(String... args) throws Exception {
        Department hr = new Department("dept01", "Human Resources", "Building A", null);
        Department eng = new Department("dept02", "Engineering", "Building B", null);

        Employee emp1 = new Employee("emp001", "Alice Smith", "alice.smith@example.com", "Recruiter", 60000, hr);
        Employee emp2 = new Employee("emp003", "Charlie Brown", "charlie.brown@example.com", "HR Assistant", 40000, hr);

        Employee emp3 = new Employee("emp002", "Bob Johnson", "bob.johnson@example.com", "Software Engineer", 80000, eng);
        Employee emp4 = new Employee("emp004", "Diana Prince", "diana.prince@example.com", "System Architect", 90000, eng);

        hr.setEmployees(Arrays.asList(emp1, emp2));
        eng.setEmployees(Arrays.asList(emp3, emp4));

        departmentRepository.saveAll(Arrays.asList(hr, eng));
    }
}

