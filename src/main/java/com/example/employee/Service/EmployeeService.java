package com.example.employee.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.employee.Entity.Department;
import com.example.employee.Entity.Employee;
import com.example.employee.Entity.EmployeeWithDepartmentDTO;
import com.example.employee.Repository.DepartmentRepository;
import com.example.employee.Repository.EmployeeRepository;
import com.lowagie.text.pdf.codec.Base64.InputStream;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    // Get all employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    
    public List<EmployeeWithDepartmentDTO> findAllWithDepartment() {
        List<EmployeeWithDepartmentDTO> employees = employeeRepository.findAllWithDepartment();
        return employees.stream().map(employee -> 
            new EmployeeWithDepartmentDTO(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getPosition(),
                employee.getSalary(),
                employee.getDepartmentId()
            )
        ).collect(Collectors.toList());
    }
    
    //reportservice
//    public String generateEmployeeReport() {
//        try {
//            // Load the Jasper template file
//            File templateFile = new File(getClass().getResource("/templates/EmployeeSubReport.jrxml").toURI());
//            JasperReport jasperReport = JasperCompileManager.compileReport(templateFile.getAbsolutePath());
//
//            // Fetch all departments with employees
//            List<Department> departments = departmentRepository.findAll();
//
//            // Convert data to JRBeanCollectionDataSource
//            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(departments);
//
//            // Parameters to pass to the report
//            Map<String, Object> parameters = new HashMap<>();
//            parameters.put("createdBy", "Your Application");
//            parameters.put("SUBREPORT_DIR", getClass().getResource("/templates/").getPath());
//
//            // Fill the report with data
//            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
//
//            // Export the report to a PDF file
//            String outputPath = System.getProperty("java.io.tmpdir") + "EmployeeReport.pdf";
//            JasperExportManager.exportReportToPdfStream(jasperPrint, new FileOutputStream(outputPath));
//
//            return outputPath;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Error: " + e.getMessage();
//        }
//    }
//    
    
    public String generateEmployeeReport() {
        try {
            // Load the Jasper template file
            File templateFile = new File(getClass().getResource("/templates/EmployeeSubReport.jrxml").toURI());
            JasperReport jasperReport = JasperCompileManager.compileReport(templateFile.getAbsolutePath());

            // Fetch all employees with their departments
//            List<Employee> employees = employeeRepository.findAll(); // Assuming employeeRepository is fetching employees with department
            List<EmployeeWithDepartmentDTO> employees = employeeRepository.findAllWithDepartment(); 
            // Parameters to pass to the report
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "Your Application");
            parameters.put("SUBREPORT_DIR", getClass().getResource("/templates/").getPath());

            // Pass the employees data to the report (instead of departments)
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employees);
            
            // Fill the report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Export the report to a PDF file
            String outputPath = System.getProperty("java.io.tmpdir") + "EmployeeReport.pdf";
            JasperExportManager.exportReportToPdfStream(jasperPrint, new FileOutputStream(outputPath));

            return outputPath;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    // Generate the employee report
//    public String generateEmployeeReport() {
//        try {
//            // Load the Jasper template file using InputStream from classpath
//            java.io.InputStream jasperTemplateInputStream = getClass().getClassLoader().getResourceAsStream("templates/EmployeeReport.jrxml");
//
//            if (jasperTemplateInputStream == null) {
//                throw new FileNotFoundException("Jasper report template not found in classpath.");
//            }
//
//            // Fetch all employees from the repository
//            List<Employee> employees = employeeRepository.findAll();
//
//            // Convert the list to JRBeanCollectionDataSource
//            JRBeanCollectionDataSource employeeDataSource = new JRBeanCollectionDataSource(employees);
//
//            // Parameters to pass to the report
//            Map<String, Object> parameters = new HashMap<>();
//            parameters.put("createdBy", "Your Application");
//
//            // Add REPORT_DATA_SOURCE parameter
//            parameters.put("REPORT_DATA_SOURCE", employeeDataSource); // Add the data source parameter here
//
//            // Compile the JRXML template into a JasperReport
//            JasperReport jasperReport = JasperCompileManager.compileReport(jasperTemplateInputStream);
//
//            // Fill the report with data using the parameters (including REPORT_DATA_SOURCE)
//            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, employeeDataSource);  // Pass the data source here
//
//            // Define the output path for the generated PDF
//            String outputPath = System.getProperty("java.io.tmpdir") + "EmployeeReport.pdf";
//
//            // Export the filled report to a PDF file
//            try (FileOutputStream outputStream = new FileOutputStream(outputPath)) {
//                JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
//            }
//
//            // Return the file path of the generated PDF
//            return outputPath;
//
//        } catch (JRException | IOException e) {
//            e.printStackTrace();
//            return "Error: " + e.getMessage();
//        }
//    }

    //reportservice

    // Get employees by department ID
    public List<Employee> getEmployeesByDepartment(String departmentId) {
        Department department = departmentRepository.findById(departmentId).orElse(null);
        return department != null ? department.getEmployees() : null;
    }

    // Add an employee to a department
    public Employee addEmployeeToDepartment(String departmentId, Employee employee) {
        Department department = departmentRepository.findById(departmentId).orElse(null);
        if (department != null) {
            employee.setDepartment(department);
            return employeeRepository.save(employee);
        }
        throw new RuntimeException("Department not found");
    }

    // Delete an employee
    public void deleteEmployee(String employeeId) {
        employeeRepository.deleteById(employeeId);
    }
    
    
    //get employee by id
    public EmployeeWithDepartmentDTO getEmployeeById(String employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee != null) {
            return new EmployeeWithDepartmentDTO(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getPosition(),
                employee.getSalary(),
                employee.getDepartmentId()
            );
        } else {
            return null;
        }
    }
    
    
    public EmployeeWithDepartmentDTO updateEmployee(String id, EmployeeWithDepartmentDTO employeeWithDepartmentDTO) {
        // Check if employee exists
        Employee existingEmployee = employeeRepository.findById(id).orElse(null);

        if (existingEmployee == null) {
            return null; // Employee not found
        }

        // Update employee details
        existingEmployee.setName(employeeWithDepartmentDTO.getName());
        existingEmployee.setEmail(employeeWithDepartmentDTO.getEmail());
        existingEmployee.setPosition(employeeWithDepartmentDTO.getPosition());
        existingEmployee.setSalary(employeeWithDepartmentDTO.getSalary());
//        existingEmployee.setDepartmentId(employeeWithDepartmentDTO.getDepartmentId());

        // Save the updated employee
        employeeRepository.save(existingEmployee);

        // Return the updated DTO (could be converted if necessary)
        return new EmployeeWithDepartmentDTO(
            existingEmployee.getId(),
            existingEmployee.getName(),
            existingEmployee.getEmail(),
            existingEmployee.getPosition(),
            existingEmployee.getSalary(),
            existingEmployee.getDepartmentId()
        );
    }
}
