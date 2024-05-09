package CRM.project.controller;

import CRM.project.dto.Requestdto;
import CRM.project.entity.Users;
import CRM.project.excel.ExcelBulkUploadUsers;
import CRM.project.repository.DepartmentRepository;
import CRM.project.repository.UsersRepository;
import CRM.project.response.Responses;
import CRM.project.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/user")
@CrossOrigin
public class UsersController {
    @Autowired
    private UsersService usersService;
    private ExcelBulkUploadUsers excelBulkUploadUsers;
    private UsersRepository usersRepository;
    private Users users;
    private DepartmentRepository departmentRepository;

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody Map<String, String> data) {
        Users user1 = usersService.addNewUser(data);
        return (user1 != null) ? new ResponseEntity<>(new Responses("00", "User details Saved Successfully"), HttpStatus.OK)
                : new ResponseEntity<>(new Responses("99", "Record not saved, Ensure staff name has not been created"), HttpStatus.OK);
    }

    @PostMapping("/findByStaff")
    public Users fetchByStaffName(@RequestBody Requestdto requestdto) {
        return usersService.fetchStaffByName(requestdto.getStaffName());
    }

    @PostMapping("/findById")
    public Users fetchById(@RequestBody Requestdto requestdto) {
        return usersService.fetchUserById(requestdto.getUnitId());
    }

    @PostMapping("/findByUnit")
    public ResponseEntity<?> fetchByUnit(@RequestBody Requestdto requestdto) {
        List<Users> unit = usersService.fetchStaffByUnit(requestdto.getUnitName());
        return new ResponseEntity<>(unit, HttpStatus.OK);
    }

    @PostMapping("/bullkUploadUsers")
    public ResponseEntity<?> uploadUsers(@RequestParam("file") MultipartFile file) {
        this.excelBulkUploadUsers.saveUsersInBulk(file);
        return new ResponseEntity<>(new Responses("00", "User upload Successfully"), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Users>> getUsers() {
        return new ResponseEntity<>(excelBulkUploadUsers.getUsers(), HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> editUser(@RequestBody Requestdto requestdto, @RequestBody Users users) {
        // Check if user with the given ID exists
        Users existingUser = usersRepository.findById(requestdto.getUnitId()).orElse(null);
        if (existingUser != null) {
            // Update user properties with values from the request body
            existingUser.setUnitName(departmentRepository.findByDepartmentName(requestdto.getUnitName()).get());
            existingUser.setUserEmail(requestdto.getUserEmail());

            // Save the updated user
            Users updatedUser = usersRepository.save(existingUser);

            return ResponseEntity.ok(updatedUser);
        }else {
            return new ResponseEntity<>(new Responses("99" ,"User ID does not Exsist"),HttpStatus.OK);
        }
        }
    }