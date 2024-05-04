package CRM.project.service;

import CRM.project.entity.Department;
import CRM.project.entity.Users;

import java.util.List;

public interface UsersService {

    Users addNewUser(Users users);

    Users fetchStaffByName(String staffName);

    Users fetchUserById(Long id);

    List<Users> fetchStaffByUnit(String unit);


}
