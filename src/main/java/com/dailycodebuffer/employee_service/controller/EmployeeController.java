package com.dailycodebuffer.employee_service.controller;

import com.dailycodebuffer.employee_service.model.Employee;
import com.dailycodebuffer.employee_service.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeRepository repository;
    private static Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @PostMapping
    public Employee add(@RequestBody Employee employee){
        logger.info("Employee add() " , employee);
        return repository.add(employee);
    }

    @GetMapping
    public List<Employee> findAll(){
        logger.info("Employee findAll() ");
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Employee findById(@PathVariable("id") Long id){
        logger.info("Employee findById() ");
        return repository.findById(id);
    }

    @GetMapping("/department/{departmentId}")
    public List<Employee> findByDepartmentId(@PathVariable("departmentId") Long departmentId){
        logger.info("Employee findByDepartmentId() ", departmentId);
        return repository.findByDeptId(departmentId);
    }
}