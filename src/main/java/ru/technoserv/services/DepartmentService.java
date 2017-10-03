package ru.technoserv.services;

import ru.technoserv.domain.Department;
import ru.technoserv.domain.Employee;

import java.util.List;

public interface DepartmentService {

    Department createDepartment (Department department);

    Department getDepartment(int deptID);

    List<Department> getAllDepartments();

    List<Department> getSubDepts(int deptId);

    Department updateDept(Department department);

    Department deleteDepartment(int deptID);

    List<Employee> getDeptEmployees(Integer depId);
}
