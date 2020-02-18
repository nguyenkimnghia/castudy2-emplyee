package com.codegym.service;

import com.codegym.model.Department;

public interface DepartmentService {
    Iterable<Department> findAll();

    Department findById(Long id);
}
