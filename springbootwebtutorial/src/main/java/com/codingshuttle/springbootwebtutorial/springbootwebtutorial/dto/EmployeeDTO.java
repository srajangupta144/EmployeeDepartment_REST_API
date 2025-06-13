package com.codingshuttle.springbootwebtutorial.springbootwebtutorial.dto;

import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.annotaions.EmployeeRoleValidation;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
//DTO= data transfer object
//work b/w presentation and business layer
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class  EmployeeDTO {
    private Long id;
    @NotNull(message = "Name is Required")
    @Size(min = 3, max = 20, message = "Number of Characters in name should be in the range:[3,10]")
    private String name;
    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;
    @Max(value = 80, message = "Max age should be 80")
    @Min(value = 18, message = "Min age should be 18")
    @NotNull(message = "Age cannot be null")
    private Integer age;
    @NotNull(message = "Role cannot be null")
//    @Pattern(regexp = "^(ADMIN|USER)$", message = "Role of Employee can be USER or ADMIN")
    @EmployeeRoleValidation
    private String role;
    @NotNull(message = "Salary of employee should not be null")
    @Positive(message = "Salary of employee should be positive")
    private Integer salary;
    @PastOrPresent(message = "Date of joining cannot be in future")
    private LocalDate dateOfJoining;
    @AssertTrue(message = "Employee should be active")
    @JsonProperty("isActive")
    private Boolean isActive;
}