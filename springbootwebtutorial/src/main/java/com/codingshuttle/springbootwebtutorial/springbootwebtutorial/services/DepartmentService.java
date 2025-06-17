package com.codingshuttle.springbootwebtutorial.springbootwebtutorial.services;

import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.dto.DepartmentDTO;
import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.dto.EmployeeDTO;
import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.entities.DepartmentEntity;
import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.entities.EmployeeEntity;
import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.repositories.DepartmentRepository;
import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.repositories.EmployeeRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public DepartmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository=employeeRepository;
        this.modelMapper = modelMapper;
    }


    public Optional<DepartmentDTO> getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .map(departmentEntity ->
                    modelMapper.map(departmentEntity, DepartmentDTO.class));
    }

    public List<DepartmentDTO> getAllDepartments(String title) {
        List<DepartmentEntity> departmentEntities;
        if (title != null && !title.isEmpty()) {
            departmentEntities = departmentRepository.findByTitleContainingIgnoreCase(title);
        } else {
            departmentEntities = departmentRepository.findAll();
        }

        return departmentEntities
                .stream()
                .map(entity -> modelMapper.map(entity, DepartmentDTO.class))
                .collect(Collectors.toList());
    }

    public DepartmentDTO createNewDepartment(@Valid DepartmentDTO inputDepartment) {
        
        DepartmentEntity toSaveEntity=modelMapper.map(inputDepartment, DepartmentEntity.class);
        DepartmentEntity savedDepartmentEntity=departmentRepository.save(toSaveEntity);
        return modelMapper.map(savedDepartmentEntity, DepartmentDTO.class);
    }

    public boolean isExistsDepartmentID(Long departmentID) {
        return departmentRepository.existsById(departmentID);
    }

    public boolean deleteDepartmentByID(Long id) {
        if(!isExistsDepartmentID(id)) throw new NoSuchElementException("No department found from tis id");
        departmentRepository.deleteById(id);
        return true;
    }

    public DepartmentDTO updateDepartmentById(Long id, DepartmentDTO departmentDTO) {
        if(!isExistsDepartmentID(id)) throw new NoSuchElementException("No department found from tis id");
        DepartmentEntity departmentEntity=modelMapper.map(departmentDTO,DepartmentEntity.class);
        departmentEntity.setId(id);
        DepartmentEntity savedDepartmentEntity=departmentRepository.save(departmentEntity);
        return modelMapper.map(savedDepartmentEntity, DepartmentDTO.class);
    }

    public DepartmentDTO updatePartialDepartmentByID(Long id, Map<String, Object> updates) {
        if(!isExistsDepartmentID(id)) throw new NoSuchElementException("No department found from tis id");
        DepartmentEntity departmentEntity=departmentRepository.findById(id).get();
        System.out.println("Received updates: " + updates);

        updates.forEach((field,value)->{
            Field fieldToBeUpdated= ReflectionUtils.findField(EmployeeEntity.class, field);

            if(fieldToBeUpdated!=null) {
                fieldToBeUpdated.setAccessible(true);                                         // .setAccessible(true) gives the access
                ReflectionUtils.setField(fieldToBeUpdated, departmentEntity, value);
            }
        });
        return modelMapper.map(departmentRepository.save(departmentEntity), DepartmentDTO.class);

    }

    public List<EmployeeDTO> getEmployeesByDepartmentId(Long departmentId) {
        DepartmentEntity department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NoSuchElementException("Department not found with id: " + departmentId));
        List<EmployeeEntity> employees = employeeRepository.findByDepartmentId(departmentId);
        return employees.stream()
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

}
