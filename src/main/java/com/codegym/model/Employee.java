package com.codegym.model;

import javax.persistence.*;
import java.time.LocalDate;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Entity
@Table(name = "employees")
public class Employee implements Validator {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String employeeId;
    private String name;
    private LocalDate dateOfBirth;
    private String gender;
    private String numberPhone;
    private String personId;
    private String email;
    private String address;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public Employee() {
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Employee.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Employee employee = (Employee) target;

        String employeeId = employee.getEmployeeId();
        String name = employee.getName();
        LocalDate dateOfBirth = employee.getDateOfBirth();
        String gender = employee.getGender();
        String numberPhone = employee.getNumberPhone();
        String personId = employee.getPersonId();
        String email = employee.getEmail();
        String address = employee.getAddress();

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employeeId", "employeeId.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateOfBirth", "dateOfBirth.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "gender.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numberPhone", "numberPhone.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "personId", "personId.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "address.empty");

        if (numberPhone.length() > 11 || numberPhone.length() < 10) {
            errors.rejectValue("numberPhone", "numberPhone.length");
        }
        if (!numberPhone.startsWith("0")) {
            errors.rejectValue("numberPhone", "numberPhone.startWith");
        }
        if (!numberPhone.matches("(^$|[0-9]*$)")) {
            errors.rejectValue("numberPhone", "numberPhone.matches");
        }
        if (!email.matches("(^[A-Za-z0-9]+[A-Za-z0-9]*@[A-Za-z0-9]+(.[A-Za-z0-9]+)$)")) {
            errors.rejectValue("email", "email.matches");
        }
    }
}
