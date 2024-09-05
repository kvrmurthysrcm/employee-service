package com.dailycodebuffer.employee.service;

import com.dailycodebuffer.employee.dto.PageRequestDto;
import com.dailycodebuffer.employee.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {
    Employee createEmployee(Employee employee) throws Exception;

    List<Employee> getAllEmployees();

    boolean deleteEmployee(Long id);

    Employee getEmployeeById(Long id);

    Employee updateEmployee(Long id, Employee employee);

    Page<Employee> getAllEmployeesUsingPagination(Pageable pageable);

    Page<Employee> getAllEmployeesUsingPaginationList(PageRequestDto dto);

    Page<Employee> getAllEmployeesUsingPaginationQueryMethod(PageRequestDto dto, String fName);

    Page<Employee> getAllEmployeesUsingPaginationNative(PageRequestDto dto, String fName);
}
