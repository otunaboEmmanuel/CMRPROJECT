package CRM.project.excel;

import CRM.project.entity.Users;
import CRM.project.repository.DepartmentRepository;
import CRM.project.repository.UsersRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelBulkUploadUsers {

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private UsersRepository usersRepository;

    //validate the file to be sure it is an Excel file
    public boolean isValidExcelFile(MultipartFile file) {
        return file.getContentType().split("/")[1].equals("xlsx");
    }


    public List<Users> saveUsersInBulk(MultipartFile file) {
        List<Users> allUsers = null;
        if (isValidExcelFile(file)) {
            try {
                XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
                XSSFSheet sheet = workbook.getSheetAt(0);

                allUsers = new ArrayList<>();
                for (int i = 1; i < sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    Users users1 = new Users();
                    if (row != null) {
                        users1.setUserEmail(String.valueOf(row.getCell(0)));
                        users1.setStaffName(String.valueOf(row.getCell(1)));
                        users1.setUnitName(departmentRepository.findByDepartmentName(String.valueOf(row.getCell(2))).get());
                    }
                    else {
                        return null;
                    }
                    allUsers.add(users1);
                }

            } catch (IOException e) {
                e.getStackTrace();
            }
            usersRepository.saveAll(allUsers);
        }
        else {
            throw new  IllegalArgumentException("This is not a valid excel file");
        }
        return allUsers;
    }

    public List<Users> getUsers(){
        return usersRepository.findAll();
    }


}

