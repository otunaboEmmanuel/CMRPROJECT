package CRM.project.service;

import CRM.project.entity.Department;

import java.util.List;

public interface DepartmentService {
    Department addNewDepartment(Department department);

    List<Department> findAllDepartmentsByName(String departmentName);
}
