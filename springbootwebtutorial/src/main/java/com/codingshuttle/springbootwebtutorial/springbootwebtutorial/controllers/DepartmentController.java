package com.codingshuttle.springbootwebtutorial.springbootwebtutorial.controllers;

import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.dto.DepartmentDTO;
import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.dto.EmployeeDTO;
import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.services.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {this.departmentService = departmentService;}

    @GetMapping("/{departmentID}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable(name="departmentID") Long id){
        Optional<DepartmentDTO> departmentDTO=departmentService.getDepartmentById(id);
        return departmentDTO.map(departmentDTO1 -> ResponseEntity.ok(departmentDTO1))
                .orElseThrow(()-> new NoSuchElementException("Resource not found"));
    }
    @GetMapping()
    public ResponseEntity<List<DepartmentDTO>> getAllDepartment(@RequestParam (required = false,name="inputTitle") String title
                                                                ){
        return ResponseEntity.ok(departmentService.getAllDepartments(title));
    }

    @GetMapping("/{departmentId}/employees")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByDepartmentId(@PathVariable Long departmentId) {
        List<EmployeeDTO> employeeDTOs = departmentService.getEmployeesByDepartmentId(departmentId);
        return ResponseEntity.ok(employeeDTOs);
    }

    @PostMapping()
    public ResponseEntity<DepartmentDTO> createNewDepartment(@RequestBody @Valid DepartmentDTO inputDepartment){
        DepartmentDTO savedDepartment=departmentService.createNewDepartment(inputDepartment);
        return new ResponseEntity<>(savedDepartment, HttpStatus.CREATED);
    }

    @DeleteMapping("/{departmentID}")
    public ResponseEntity<Boolean> deleteDepartmentById(@PathVariable(name ="departmentID") Long id){
        boolean gotDeleted=departmentService.deleteDepartmentByID(id);
        if(gotDeleted)return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
    }
    @PutMapping(path="/{departmentID}")
    public ResponseEntity<DepartmentDTO> updateDepartmentById(@RequestBody DepartmentDTO departmentDTO, @PathVariable(name="departmentID") Long id){
        return ResponseEntity.ok(departmentService.updateDepartmentById(id, departmentDTO));
    }


    @PatchMapping(path = "/{departmentID}")
    public ResponseEntity<DepartmentDTO> updatePartialDepartmentById(@RequestBody Map<String, Object> updates,
                                                                 @PathVariable(name="departmentID") Long id){
        DepartmentDTO departmentDTO= departmentService.updatePartialDepartmentByID(id, updates);
        if(departmentDTO==null)ResponseEntity.notFound().build();
        return ResponseEntity.ok(new DepartmentDTO());
    }

}
