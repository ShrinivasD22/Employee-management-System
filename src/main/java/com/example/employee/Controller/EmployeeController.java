package com.example.employee.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.employee.Entity.Employee;
import com.example.employee.Entity.EmployeeWithDepartmentDTO;
import com.example.employee.Service.EmployeeService;

import net.sf.jasperreports.repo.InputStreamResource;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // a. Get all employees
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }
    
    @GetMapping("/with-department")
    public ResponseEntity<List<EmployeeWithDepartmentDTO>> getAllEmployeesWithDepartment() {
        List<EmployeeWithDepartmentDTO> employees = employeeService.findAllWithDepartment();
        return ResponseEntity.ok(employees);
    }

    // Update Employee
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeWithDepartmentDTO> updateEmployee(@PathVariable String id, 
                                                                   @RequestBody EmployeeWithDepartmentDTO employeeWithDepartmentDTO) {
        EmployeeWithDepartmentDTO updatedEmployee = employeeService.updateEmployee(id, employeeWithDepartmentDTO);
        
        if (updatedEmployee != null) {
            return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // If employee not found
        }
    }
    
    // c. Get employees in a department
    @GetMapping("/department/{departmentId}")
    public List<Employee> getEmployeesByDepartment(@PathVariable String departmentId) {
        return employeeService.getEmployeesByDepartment(departmentId);
    }

    // d. Add a new employee to a department
    @PostMapping("/department/{departmentId}")
    public Employee addEmployeeToDepartment(@PathVariable String departmentId, @RequestBody Employee employee) {
        return employeeService.addEmployeeToDepartment(departmentId, employee);
    }

    // e. Delete an employee
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable String id) {
        employeeService.deleteEmployee(id);
    }
    
    //get employee by id
    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeWithDepartmentDTO> getEmployeeById(@PathVariable String employeeId) {
        EmployeeWithDepartmentDTO employee = employeeService.getEmployeeById(employeeId);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    
//    @GetMapping("/employees")
//    public String generateEmployeeReport() {
//        return employeeService.generateEmployeeReport();
//    }
    @GetMapping("/employees")
    public ResponseEntity<Object> downloadEmployeeReport() {
        try {
            // Generate the report and get the output path
            String filePath = employeeService.generateEmployeeReport();

            // Load the generated PDF file
            File pdfFile = new File(filePath);
            if (!pdfFile.exists()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to generate the report.");
            }

            // Prepare the file for download using FileSystemResource
            FileSystemResource resource = new FileSystemResource(pdfFile);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=EmployeeReport.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(pdfFile.length())
                    .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while generating or downloading the report: " + e.getMessage());
        }
    }
}
