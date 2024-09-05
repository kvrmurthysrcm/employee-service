package com.dailycodebuffer.employee.repository;

import com.dailycodebuffer.employee.entity.EmployeeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository  extends JpaRepository<EmployeeEntity, Long> {

    Page<EmployeeEntity> findAllByFirstName(String firstName, Pageable pageable);
    Page<EmployeeEntity>  findAllByLastName(String lastName, Pageable pageable);
    Page<EmployeeEntity>  findAllByEmailId(String emailId, Pageable pageable);

    List<EmployeeEntity>  findAllByFirstName(String firstName);
    List<EmployeeEntity>  findAllByLastName(String lastName);
    List<EmployeeEntity>  findAllByEmailId(String emailId);

    // Matches where 'name' contains the provided string
    List<EmployeeEntity> findByFirstNameContaining(String name);
    // Matches where 'name' starts with the provided string
    List<EmployeeEntity> findByFirstNameStartingWith(String name);
    // Matches where 'name' ends with the provided string
    List<EmployeeEntity> findByFirstNameEndingWith(String name);

    // Custom query using LIKE with wildcards (%)
    //@Query("select * from employees where first_name LIKE %:firstName%")
    //List<EmployeeEntity> searchByFirstName(@Param("firstName") String firstName);

    // the backslash (\\) is used to escape special characters.
    // @Query("select * from employees where first_name LIKE %:firstName% ESCAPE '\\'")
    // List<EmployeeEntity> searchByFirstNameWithEscape(@Param("firstName") String firstName);

    @Query(value="select * from employees where first_name = :fName",
           countQuery = "select count(*) from employees where first_name = :fName",
    nativeQuery= true)
    Page<EmployeeEntity> findAllByFirstNameNative(String fName, Pageable pageable);
}