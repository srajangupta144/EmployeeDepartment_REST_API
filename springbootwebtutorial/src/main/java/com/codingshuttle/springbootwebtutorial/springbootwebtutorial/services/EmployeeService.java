package com.codingshuttle.springbootwebtutorial.springbootwebtutorial.services;

import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.configs.MapperConfig;
import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.dto.DepartmentDTO;
import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.dto.EmployeeDTO;
import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.entities.DepartmentEntity;
import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.entities.EmployeeEntity;
import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.repositories.DepartmentRepository;
import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository,DepartmentRepository departmentRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
        this.departmentRepository=departmentRepository;
    }

    public Optional<EmployeeDTO> getEmployeeById(Long id){                                                  //optional used to handle things which may contain null values
//        Optional<EmployeeEntity> employeeEntity= employeeRepository.findById(id);
//        return employeeEntity.map(employeeEntity1 -> modelMapper.map(employeeEntity1, EmployeeDTO.class));

        return employeeRepository.findById(id)
                .map(employeeEntity ->
                        modelMapper.map(employeeEntity,EmployeeDTO.class));
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> employeeEntities=employeeRepository.findAll();
        return employeeEntities
                .stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDTO.class))
                .collect(Collectors.toList());
    }
    public EmployeeDTO createNewEmployee(EmployeeDTO inputEmployee) {
        Long departmentId = inputEmployee.getDepartmentId();
        DepartmentEntity department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Department not found with id: " + departmentId
                ));
        EmployeeEntity toSaveEntity = modelMapper.map(inputEmployee, EmployeeEntity.class);
        toSaveEntity.setDepartment(department); // Ensure department is set
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(toSaveEntity);
        return modelMapper.map(savedEmployeeEntity, EmployeeDTO.class);
    }
    public EmployeeDTO updateEmployeeById(Long employeeID, EmployeeDTO employeeDTO) {
        // Fetch existing employee
        EmployeeEntity existingEntity = employeeRepository.findById(employeeID)
                .orElseThrow(() -> new NoSuchElementException("No employee found with id: " + employeeID));

        // Check and update department if provided
        if (employeeDTO.getDepartmentId() != null) {
            Long departmentId = employeeDTO.getDepartmentId();
            DepartmentEntity department = departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new NoSuchElementException("Department not found with id: " + departmentId));
            existingEntity.setDepartment(department);
        }

        // Map non-null fields from DTO to existing entity
        modelMapper.map(employeeDTO, existingEntity);
        existingEntity.setId(employeeID); // Ensure ID remains unchanged

        // Save and return
        EmployeeEntity savedEntity = employeeRepository.save(existingEntity);
        return modelMapper.map(savedEntity, EmployeeDTO.class);
    }


    public EmployeeDTO updatePartialEmployeeByID(Long employeeID, Map<String, Object> updates) {
        // Fetch existing employee
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeID)
                .orElseThrow(() -> new NoSuchElementException("No employee found with id: " + employeeID));

        updates.forEach((field, value) -> {
            // Handle department_id separately (relationship, not a direct field)
            if ("department_id".equals(field)) {
                Long departmentId = Long.parseLong(value.toString());
                DepartmentEntity department = departmentRepository.findById(departmentId)
                        .orElseThrow(() -> new NoSuchElementException("Department not found with id: " + departmentId));
                employeeEntity.setDepartment(department);
            } else {
                // Use reflection for other fields
                Field fieldToUpdate = ReflectionUtils.findField(EmployeeEntity.class, field);
                if (fieldToUpdate != null) {
                    fieldToUpdate.setAccessible(true);
                    ReflectionUtils.setField(fieldToUpdate, employeeEntity, value);
                }
            }
        });

        // Save and return
        EmployeeEntity savedEntity = employeeRepository.save(employeeEntity);
        return modelMapper.map(savedEntity, EmployeeDTO.class);
    }

    public boolean deleteEmployeeById(Long id) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("No employee was found from this id"));
        employeeRepository.deleteById(id);
        return true;
    }

    public DepartmentDTO getDepartmentByEmployeeId(Long employeeId) {
        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NoSuchElementException("Employee not found with id: " + employeeId));
        DepartmentEntity department = employee.getDepartment();
        return modelMapper.map(department, DepartmentDTO.class);
    }
}
