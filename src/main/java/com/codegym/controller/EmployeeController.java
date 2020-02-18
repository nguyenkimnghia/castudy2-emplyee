package com.codegym.controller;

import com.codegym.model.Department;
import com.codegym.model.Employee;
import com.codegym.service.impl.DepartmentServiceImpl;
import com.codegym.service.impl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class EmployeeController {
    @Autowired
    EmployeeServiceImpl employeeService;
    @Autowired
    DepartmentServiceImpl departmentService;

    @ModelAttribute("departments")
    public Iterable<Department> departments() {
        return departmentService.findAll();
    }

    @GetMapping("/employees")
    public ModelAndView listProduct(@RequestParam("s") Optional<String> s, @PageableDefault(value = 5, sort = "employeeId") Pageable pageable) {
        Page<Employee> employees;
        if (s.isPresent()) {
            employees = employeeService.findAllByEmployeeIdContainsOrNameContains(s.get(), s.get(), pageable);
        } else {
            employees = employeeService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/employee/list");
        modelAndView.addObject("employees", employees);
        return modelAndView;
    }

    @GetMapping("/create-employee")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/employee/create");
        modelAndView.addObject("employee", new Employee());
        return modelAndView;
    }

    @PostMapping("/create-employee")
    public String saveProduct(@Valid @ModelAttribute("employee") Employee employee, BindingResult result) {
        new Employee().validate(employee, result);
        if (result.hasFieldErrors()) {
            return "/employee/create";
        } else {
            employeeService.save(employee);
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("employee", new Employee());
            modelAndView.addObject("message", "\n" +
                    "Add new employees successfully !!!");
            return "redirect:/employees";
        }
    }

    @GetMapping("/edit-employee/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Employee employee = employeeService.findById(id);
        if (employee != null) {
            ModelAndView modelAndView = new ModelAndView("/employee/edit");
            modelAndView.addObject("employee", employee);
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/edit-employee")
    public ModelAndView updateProduct(@Valid @ModelAttribute("employee") Employee employee, BindingResult result) {
        new Employee().validate(employee, result);

        Employee employee1 = employeeService.findById(employee.getId());
        employee1.setName(employee.getName());
        employee1.setDateOfBirth(employee.getDateOfBirth());
        employee1.setGender(employee.getGender());
        employee1.setNumberPhone(employee.getNumberPhone());
        employee1.setPersonId(employee.getPersonId());
        employee1.setEmail(employee.getEmail());
        employee1.setAddress(employee.getAddress());
        employee1.setDepartment(employee.getDepartment());
        if (result.hasFieldErrors()) {
            ModelAndView modelAndView = new ModelAndView("/employee/edit");
            return modelAndView;
        }
        {
            if (employee1 != null) {
                employeeService.save(employee1);
                ModelAndView modelAndView = new ModelAndView("/employee/edit");
                modelAndView.addObject("employee", employee1);
                modelAndView.addObject("message", "\n" +
                        "Fix the employee successfully !!!");
                return modelAndView;
            } else {
                ModelAndView modelAndView = new ModelAndView("/error.404");
                return modelAndView;
            }
        }
    }

    @GetMapping("/delete-employee/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Employee employee = employeeService.findById(id);
        if (employee != null) {
            ModelAndView modelAndView = new ModelAndView("/employee/delete");
            modelAndView.addObject("employee", employee);
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/delete-employee")
    public String deleteCustomer(@ModelAttribute("employee") Employee employee) {
        employeeService.remove(employee.getId());
        return "redirect:employees";
    }
}
