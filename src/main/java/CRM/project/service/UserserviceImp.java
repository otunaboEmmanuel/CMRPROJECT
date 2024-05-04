package CRM.project.service;

import CRM.project.entity.Users;
import CRM.project.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserserviceImp implements UsersService {
    @Autowired
    private UsersRepository usersRepository;
    @Override
    public Users addNewUser(Users users) {
        Users users1=usersRepository.findByStaffName(users.getStaffName()).orElse(null);
        return users1== null ? usersRepository.save(users) : null;
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
        return usersRepository.findByUnitName(unitName);
    }


}
