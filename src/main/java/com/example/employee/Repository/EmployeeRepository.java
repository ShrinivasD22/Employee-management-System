package com.example.employee.Repository;




import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.employee.Entity.Employee;
import com.example.employee.Entity.EmployeeWithDepartmentDTO;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

	   @Query("SELECT new com.example.employee.Entity.EmployeeWithDepartmentDTO(e.id, e.name, e.email, e.position, e.salary, e.department.id) " +
	           "FROM Employee e")
    List<EmployeeWithDepartmentDTO> findAllWithDepartment();
    
    List<Employee> findAll();
}

