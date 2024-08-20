package com.dailycodebuffer.employee_service.repository;

import com.dailycodebuffer.employee_service.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.*;

@Repository
public class EmployeeRepository {

    private List<Employee> employees = new ArrayList<Employee>();

    public Employee add(Employee employee){
        employees.add(employee);
        return employee;
    }

    public Employee findById(Long id){
        return employees.stream().filter( department -> department.id().equals(id))
                .findFirst()
                .orElseThrow();
    }

    public List<Employee> findAll(){
        return employees;
    }

    public List<Employee> findByDeptId(Long departmentId){
       return employees.stream().filter(employee -> employee.departmentId().equals(departmentId))
               .toList();
    }

}
