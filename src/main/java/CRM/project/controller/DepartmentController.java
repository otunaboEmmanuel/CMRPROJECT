package CRM.project.controller;

import CRM.project.dto.Requestdto;
import CRM.project.entity.Department;
import CRM.project.entity.Users;
import CRM.project.repository.DepartmentRepository;
import CRM.project.repository.UsersRepository;
import CRM.project.response.Responses;
import CRM.project.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private DepartmentRepository departmentRepository;



    @PostMapping("/addDepartment")
    public ResponseEntity<?> addDepartment(@RequestBody Department department)
    {
        Department department1=departmentService.addNewDepartment(department);
        return department1==null? new ResponseEntity<>(new Responses("00", "department details Saved Successfully"), HttpStatus.OK)
                : new ResponseEntity<>(new Responses("99", "Record not saved, Ensure department name does not exist"), HttpStatus.OK);
    }

    @PostMapping("/addUserToDepartment")
    public ResponseEntity<?>addUserToDepartment(@RequestBody Requestdto department)
    {
        Users users=usersRepository.findById(department.getUnitId()).orElse(null);
        if (users!=null)
        {
            Department department1=new Department();
            department1.setUser(users);
            department1.setDepartmentName(department.getDepartmentName());
            departmentRepository.save(department1);
            return new ResponseEntity<>(new Responses("00",
                    "department details Saved Successfully"), HttpStatus.OK);
        }else
            return new ResponseEntity<>(new Responses("99", "Record not saved, Ensure UserId exits"), HttpStatus.OK);
    }
    @PostMapping("/findDepartmentsByDepartmentName")
    public ResponseEntity<?> findAllDepartmentsByName(@RequestBody Requestdto requestdto) {


        List<Department> allDepartments = departmentService.findAllDepartmentsByName(requestdto.getDepartmentName());
        return new ResponseEntity<>(allDepartments, HttpStatus.OK);
    }
}
