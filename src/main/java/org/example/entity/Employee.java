package org.example.entity;

import io.ebean.annotation.NotNull;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "employee")
public class Employee {
    @NotNull
    @Id
    private Integer id;

    private String name;
    private Integer age;
    private String email;
    private String mobileNumber;
}
