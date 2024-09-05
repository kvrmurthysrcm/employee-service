package com.dailycodebuffer.employee.controller;

import com.dailycodebuffer.employee.dto.PageRequestDto;
import com.dailycodebuffer.employee.model.Employee;
import com.dailycodebuffer.employee.service.EmployeeService;
import com.dailycodebuffer.employee.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@CrossOrigin(origins = "http://localhost:3000, http://3.131.95.185/:3000")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
    private EmployeeService employeeService;

    @Autowired
    private RedisService redisService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public String home(){
        return "Hello Home JWT !!!";
    }

    @GetMapping("/error")
    public String error(){
        return "Unknown error occurred!!";
    }

    @GetMapping("/logout")
    public String logout(){
        return "Logout from app...!!";
    }

    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee) throws Exception {
        System.out.println("inside createEmployee() @ EmployeeController class.");
        Employee emp = null;
        try {
            emp = employeeService.createEmployee(employee);
            redisService.setValue(emp.getId()+"", emp);
            System.out.println("EmployeeController: Created emp: " + emp);
        } catch(Exception e){
           System.out.println("EmployeeController: " + e.getMessage());
           // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
           //         .body(e.getMessage());
           throw e;
        }
        return emp;
    }

    //@CrossOrigin(origins = "*")
    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        System.out.println("Inside getAllEmployees()......................");
        return employeeService.getAllEmployees();
    }

    @PostMapping("/employeespn")
    public Page<Employee> getAllEmployeesUsingPagination(@RequestBody PageRequestDto dto){

        Pageable pageable = new PageRequestDto().getPageable(dto);

        Page<Employee> employees = employeeService.getAllEmployeesUsingPagination(pageable);
        System.out.println("getAllEmployeesUsingPagination():: employee: " + employees);
        return employees;
    }

    @PostMapping("/employeespnl")
    public Page<Employee> getAllEmployeesUsingPaginationList(@RequestBody PageRequestDto dto){

        //Pageable pageable = new PageRequestDto().getPageable(dto);

        Page<Employee> employees = employeeService.getAllEmployeesUsingPaginationList(dto);
        System.out.println("getAllEmployeesUsingPagination():: employee: " + employees);
        return employees;
    }

    @PostMapping("/employeespn/queryMethod/{fName}")
    public Page<Employee> getAllEmployeesUsingPaginationQueryMethod(@RequestBody PageRequestDto dto, @PathVariable(value="fName") String fName){

        Page<Employee> employees = employeeService.getAllEmployeesUsingPaginationQueryMethod(dto, fName);
        System.out.println("getAllEmployeesUsingPaginationQueryMethod():: fName: " + fName);
        return employees;
    }

    @PostMapping("/employeespn/native/{fName}")
    public Page<Employee> getAllEmployeesUsingPaginationQueryMethodNative(@RequestBody PageRequestDto dto, @PathVariable(value="fName") String fName){

        Page<Employee> employees = employeeService.getAllEmployeesUsingPaginationNative(dto, fName);
        System.out.println("getAllEmployeesUsingPaginationQueryMethod():: fName: " + fName);
        return employees;
    }


    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteEmployee(@PathVariable Long id){
        boolean deleted = false;
        deleted = employeeService.deleteEmployee(id);

        Map<String, Boolean> response = new HashMap();
        response.put("deleted", deleted);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
        Employee employee = null;
        try {
            employee = (Employee) redisService.getValue(id + "");
            if(employee == null){
                employee = employeeService.getEmployeeById(id);
                redisService.setValue("" + employee.getId(), employee);
            } else{
                System.out.println("Employee from cache: " + employee);
            }
        } catch(Exception e){
            System.out.println("Exception occurred: " + e.getMessage());
            return ResponseEntity.ok(employee);
        }
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,
                                                   @RequestBody Employee employee){
        Employee employee1 = null;
        employee1 = employeeService.updateEmployee(id, employee);
        redisService.setValue("" + employee.getId(), employee); // update the cache also!
        return ResponseEntity.ok(employee);
    }
}