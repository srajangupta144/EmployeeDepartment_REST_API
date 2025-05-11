package com.codingshuttle.springbootwebtutorial.springbootwebtutorial.services;

import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.configs.MapperConfig;
import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.dto.EmployeeDTO;
import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.entities.EmployeeEntity;
import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
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
        EmployeeEntity toSaveEntity= modelMapper.map(inputEmployee,EmployeeEntity.class);
        EmployeeEntity savedEmployeeEntity= employeeRepository.save(toSaveEntity);
        return modelMapper.map(savedEmployeeEntity,EmployeeDTO.class);
    }
    public EmployeeDTO updateEmployeeById(Long employeeID, EmployeeDTO employeeDTO) {
        boolean exists=isExistsEmployeeID(employeeID);
        if (!exists) return null;
        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
        employeeEntity.setId(employeeID);
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);
        return modelMapper.map(savedEmployeeEntity, EmployeeDTO.class);
    }

    public boolean isExistsEmployeeID(Long employeeID) {
        return employeeRepository.existsById(employeeID);
    }

    public EmployeeDTO updatePartialEmployeeByID(Long employeeID, Map<String, Object> updates) {
        boolean exists=isExistsEmployeeID(employeeID);
        if (!exists) return null;
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeID).get();
        System.out.println("Received updates: " + updates);

        updates.forEach((field, value) ->  {
            Field fieldToBeUpdated = ReflectionUtils.findField(EmployeeEntity.class, field);  //Reflection is used to find field in employeeentity class
            if (fieldToBeUpdated != null) {
                fieldToBeUpdated.setAccessible(true);                                         // .setAccessible(true) gives the access
                ReflectionUtils.setField(fieldToBeUpdated, employeeEntity, value);
            }
        });
        return modelMapper.map(employeeRepository.save(employeeEntity),EmployeeDTO.class);
    }
    public boolean deleteEmployeeById(Long id) {
        if(!isExistsEmployeeID(id))return false;
        employeeRepository.deleteById(id);
        return true;
    }
}
