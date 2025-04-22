package com.codingshuttle.springbootwebtutorial.springbootwebtutorial.controllers;

import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.dto.EmployeeDTO;
import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.repositories.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RestController
@RequestMapping("/employees")
public class EmployeeController {
//    @GetMapping(path = "/getSecretMessage")
//    public String getMySuperSecretMessage(){
//        return "Secret message: uhagerbbuo@ovifnvoin";

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @GetMapping("/{employeeID}")
    public EmployeeDTO getEmployeeById(@PathVariable Long employeeID){
        return new EmployeeDTO(employeeID, "Anuj","anujow@gmail.com", 24, LocalDate.of(2024,1,24),true);
    }

    @GetMapping()
    public String getEmployee(@RequestParam(required = false,name = "inputAge")Integer age,
                              @RequestParam(required = false)String sortBy){
        return "Hello age"+age+" "+sortBy;
    }

    @PostMapping()
    public EmployeeDTO createEmployee(@RequestBody EmployeeDTO inputEmployee){
        inputEmployee.setId(101L);
        return inputEmployee;
    }

    @PatchMapping()
    public String hey(){
        return "Hello from patch";
    }
}
