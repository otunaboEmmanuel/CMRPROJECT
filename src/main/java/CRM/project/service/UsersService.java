package CRM.project.service;

import CRM.project.entity.Users;

import java.util.List;
import java.util.Map;

public interface UsersService {

    Users addNewUser(Map<String,String> data);

    Users fetchStaffByName(String staffName);

    Users fetchUserById(Long id);

    List<Users> fetchStaffByUnit(String unit);


}
