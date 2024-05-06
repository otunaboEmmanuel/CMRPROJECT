package CRM.project.service;

import CRM.project.entity.Department;
import CRM.project.entity.Users;
import CRM.project.repository.DepartmentRepository;
import CRM.project.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class UserserviceImp implements UsersService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private DepartmentRepository departmentRepository;
    @Override
    public Users addNewUser(Map<String,String> data) {
        Users users1=usersRepository.findByStaffName(data.get("technician")).orElse(null);
        Department department1=departmentRepository.findByDepartmentName(data.get("unit")).orElse(null);

        if(department1 != null && users1 == null) {
            Users users = new Users();
            users.setStaffName(data.get("technician"));
            users.setUnitName(department1);
            users.setUserEmail(data.get("email"));
            return usersRepository.save(users);
        }
        return null;
    }

    @Override
    public Users fetchStaffByName(String staffName) {
        return usersRepository.findByStaffName(staffName).orElse(null);
    }

    @Override
    public Users fetchUserById(Long id) {
        return usersRepository.findById(id).get();
    }

    @Override
    public List<Users> fetchStaffByUnit(String unitName) {
        log.info(unitName);
        Optional<Department> department = departmentRepository.findByDepartmentName(unitName);
        if(department.isPresent()) {
            return usersRepository.findByUnitName(department.get());
        }
        return null;
    }


}
