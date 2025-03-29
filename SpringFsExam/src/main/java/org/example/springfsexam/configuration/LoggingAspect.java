package org.example.springfsexam.configuration;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* org.example.springfsexam.Service.EmployeService.*(..))")
    public void employeServiceMethods() {}

    @AfterReturning("employeServiceMethods()")
    public void logAfter() {
        System.out.println("Modification de la base de données détectée !");
    }
}