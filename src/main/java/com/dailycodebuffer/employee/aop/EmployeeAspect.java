package com.dailycodebuffer.employee.aop;

import com.dailycodebuffer.employee.model.Employee;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class EmployeeAspect {

    @Before("execution(* com.dailycodebuffer.employee.config.SecurityConfig.*(..))")
    public void beforeAdviceForSecurityConfig(JoinPoint joinPoint){
        System.out.println("Request to " + joinPoint.getSignature() + " started at " + new Date() );
    }

    @Before("execution(* com.dailycodebuffer.employee.dto.PageRequestDto.*(..))")
    public void beforeAdviceForPageRequestDto(JoinPoint joinPoint){
        System.out.println("Request to " + joinPoint.getSignature() + " started at " + new Date() );
    }

    @Before("execution(* com.dailycodebuffer.employee.controller.EmployeeController.*(..))")
    public void beforeAdvice(JoinPoint joinPoint){
        System.out.println("Request to " + joinPoint.getSignature() + " started at " + new Date() );
    }

    @After("execution(* com.dailycodebuffer.employee.controller.EmployeeController.*(..))")
    public void aftereAdvice(JoinPoint joinPoint){
        System.out.println("Request to " + joinPoint.getSignature() + " ended at " + new Date() );
    }

    @Before("execution(* com.dailycodebuffer.employee.services.EmployeeServiceImpl.*(..))")
    public void beforeAdviceForService(JoinPoint joinPoint){
        System.out.println("Request to service layer: " + joinPoint.getSignature() + " started at " + new Date() );
    }

    @After("execution(* com.dailycodebuffer.employee.services.EmployeeServiceImpl.*(..))")
    public void aftereAdviceForService(JoinPoint joinPoint){
        System.out.println("Request to service layer: " + joinPoint.getSignature() + " ended at " + new Date() );
    }

    @AfterReturning(value= "execution(* com.dailycodebuffer.employee.services.EmployeeServiceImpl.createEmployee(..))" , returning = "employee")
    public void afterReturningAdviceForService(JoinPoint joinPoint, Employee employee){
        System.out.println("Request to service layer: Create Employee" + employee  );
    }

    @AfterThrowing(value= "execution(* com.dailycodebuffer.employee.services.EmployeeServiceImpl.createEmployee(..))" , throwing = "exception")
    public void afterThrowingAdviceForService(JoinPoint joinPoint, Exception exception){
        System.out.println("Request to service layer: Create Employee: Exception occurred: " + exception.getMessage()  );
    }

    @Around(value= "execution(* com.dailycodebuffer.employee.services.EmployeeServiceImpl.createEmployee(..))")
    public Employee aroundAdviceForService(ProceedingJoinPoint proceedingJoinPoint){
        System.out.println("Inside aroundAdviceForService : Create Employee: started at: " + new Date()  );
        try{
            return (Employee) proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            System.out.println("Inside aroundAdviceForService : Create Employee: failed at : " + new Date()  );
            //throw new RuntimeException(e);
        }
        System.out.println("Inside aroundAdviceForService : Create Employee: ended at: " + new Date()  );
        return null;
    }
}