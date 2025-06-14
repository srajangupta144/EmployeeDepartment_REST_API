package com.codingshuttle.springbootwebtutorial.springbootwebtutorial.controllers;

import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.dto.DepartmentDTO;
import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.dto.EmployeeDTO;
import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.entities.EmployeeEntity;
import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.repositories.EmployeeRepository;
import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;


@RestController
@RequestMapping("/employees")
public class EmployeeController {
//    @GetMapping(path = "/getSecretMessage")
//    public String getMySuperSecretMessage(){
//        return "Secret message: uhagerbbuo@ovifnvoin";


    private final EmployeeService employeeService;              //Service connected

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping("/{employeeID}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable(name="employeeID") Long id){
        Optional<EmployeeDTO> employeeDTO= employeeService.getEmployeeById(id);
        return employeeDTO.map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1))
                            .orElseThrow(()-> new NoSuchElementException("Element not found"));
    }

    @GetMapping()
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@RequestParam(required = false,name = "inputAge")Integer age,
                            @RequestParam(required = false)String sortBy){
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{employeeId}/department")
    public ResponseEntity<DepartmentDTO> getDepartmentByEmployeeId(@PathVariable Long employeeId) {
        DepartmentDTO departmentDTO = employeeService.getDepartmentByEmployeeId(employeeId);
        return ResponseEntity.ok(departmentDTO);
    }

    @PostMapping()
    public ResponseEntity<EmployeeDTO> createNewEmployee(@RequestBody @Valid EmployeeDTO inputEmployee){
        EmployeeDTO savedEmployee= employeeService.createNewEmployee(inputEmployee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @PutMapping(path="/{employeeID}")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@RequestBody EmployeeDTO employeeDTO, @PathVariable(name="employeeID") Long id){
        return ResponseEntity.ok(employeeService.updateEmployeeById(id, employeeDTO));
    }

    @DeleteMapping(path="/{employeeID}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable (name="employeeID") Long id){
        boolean gotDeleted=employeeService.deleteEmployeeById(id);
        if(gotDeleted)return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
    }

    @PatchMapping(path = "/{employeeID}")
    public ResponseEntity<EmployeeDTO> updatePartialEmployeeById(@RequestBody Map<String, Object> updates,
                                                 @PathVariable(name="employeeID") Long id){
        EmployeeDTO employeeDTO= employeeService.updatePartialEmployeeByID(id, updates);
        if(employeeDTO==null)ResponseEntity.notFound().build();
        return ResponseEntity.ok(employeeDTO);
    }
}
