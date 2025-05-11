package com.codingshuttle.springbootwebtutorial.springbootwebtutorial.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity                                 //tells JPA or Hibernate that convert this class as Table in database
@Table(name = "employees")              //give name to table else class name is taken by default

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeEntity {
    @Id                                 //primaryKey define
    @GeneratedValue(strategy = GenerationType.AUTO)     //for increment the value
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private LocalDate dateOfJoining;
    private Boolean isActive;


}
