package com.dailycodebuffer.employee.service;

import com.dailycodebuffer.employee.dto.PageRequestDto;
import com.dailycodebuffer.employee.entity.EmployeeEntity;
import com.dailycodebuffer.employee.model.Employee;
import com.dailycodebuffer.employee.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Override
    public Page<Employee> getAllEmployeesUsingPagination(Pageable pageable) {
        Page<EmployeeEntity> employeeEntitiesPage
                = employeeRepository.findAll(pageable);

        return employeeEntitiesPage.map(this::toEmployee);
    }

    // TODO: Review this implementation....
    // THis doesn't work the same way as the getAllEmployeesUsingPagination() method !!
    @Override
    public Page<Employee> getAllEmployeesUsingPaginationList(PageRequestDto dto) {

        List<Employee> employeeList =  getAllEmployees();
        // 1. PageListHolder
        PagedListHolder<Employee> pagedListHolder = new PagedListHolder<Employee>(employeeList);
        pagedListHolder.setPage(dto.getPageNo());
        pagedListHolder.setPageSize(dto.getPageSize());

        // 2. Property Comparator
        List<Employee> pageSlice = new ArrayList<>(pagedListHolder.getPageList());
        boolean ascending = dto.getSort().isAscending();
        System.out.println("calling ....PropertyComparator.sort("+pageSlice+", new MutableSortDefinition("+dto.getSortByColumn()+", true, "+ascending+")");
        PropertyComparator.sort(pageSlice, new MutableSortDefinition(dto.getSortByColumn(), true, ascending));

        // 3. PageImpl
        // TODO: need to check usage of this object PageImpl and find alternative to fix the warning.
        return new PageImpl<>(pageSlice, new PageRequestDto().getPageable(dto), employeeList.size());
    }

    @Override
    public Page<Employee> getAllEmployeesUsingPaginationQueryMethod(PageRequestDto dto, String fname) {
        Pageable pageable = new PageRequestDto().getPageable(dto);
        // make the custom query here...search by first name
        Page<EmployeeEntity> byFirstName = employeeRepository.findAllByFirstName(fname, pageable);

        return byFirstName.map(this::toEmployee);
    }

    @Override
    public Page<Employee> getAllEmployeesUsingPaginationNative(PageRequestDto dto, String fName) {

        Pageable pageable = new PageRequestDto().getPageable(dto);
        // make the custom query here...search by first name
        Page<EmployeeEntity> byFirstName = employeeRepository.findAllByFirstNameNative(fName, pageable);
        return byFirstName.map(this::toEmployee);
    }

    public Employee toEmployee(EmployeeEntity entity) {
        Employee employee = new Employee();
        employee.setId(entity.getId());
        employee.setFirstName(entity.getFirstName());
        employee.setLastName(entity.getLastName());
        employee.setEmailId(entity.getEmailId());
        return employee;
    }

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    @CachePut(value="employees", key="#employee.id")
    public Employee createEmployee(Employee employee) throws Exception {

        EmployeeEntity employeeEntity = new EmployeeEntity();
        BeanUtils.copyProperties(employee, employeeEntity);
        employeeEntity = employeeRepository.save(employeeEntity);
        employee.setId(employeeEntity.getId());
        return employee;
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<EmployeeEntity> employeeEntities
                = employeeRepository.findAll();
        List <Employee> employees = employeeEntities
                .stream()
                .map(emp -> new Employee(emp.getId(),
                        emp.getFirstName()
                , emp.getLastName(), emp.getEmailId()))
                .collect(Collectors.toUnmodifiableList());
        return employees;
    }

    @Override
    @CacheEvict(value="employees", key="#id")
    public boolean deleteEmployee(Long id) {
        EmployeeEntity employee = employeeRepository.findById(id).get();
        employeeRepository.delete(employee);
        return true;
    }

    @Override
    @Cacheable(value="employees", key="#id")
    public Employee getEmployeeById(Long id) {
        EmployeeEntity employeeEntity
                = employeeRepository.findById(id).get();
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeEntity, employee);
        return employee;
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        EmployeeEntity employeeEntity
                = employeeRepository.findById(id).get();
        employeeEntity.setEmailId(employee.getEmailId());
        employeeEntity.setFirstName(employee.getFirstName());
        employeeEntity.setLastName(employee.getLastName());
        employeeRepository.save(employeeEntity);
        return employee;
    }
}