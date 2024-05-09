package CRM.project.excel;

import CRM.project.entity.SubCategory;
import CRM.project.repository.DepartmentRepository;
import CRM.project.repository.SubCategoryRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelBulkUploadSubCategory {

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private SubCategoryRepository subCategoryRepository;

    //validate the file to be sure it is an Excel file
    public boolean isValidExcelFile(MultipartFile file) {
        return file.getContentType().split("/")[1].equals("xlsx");
    }


    public List<SubCategory> saveSubCategoryBulk(MultipartFile file) {
        List<SubCategory> subCategoryList = null;
        if (isValidExcelFile(file)) {
            try {
                XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
                XSSFSheet sheet = workbook.getSheetAt(0);

                subCategoryList = new ArrayList<>();
                for (int i = 1; i < sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    SubCategory subCategory = new SubCategory();
                    if (row != null) {
                        subCategory.setSubCategoryName(String.valueOf(row.getCell(0)));
                        subCategory.setSla((int) row.getCell(1).getNumericCellValue());
                        subCategory.setDepartment(departmentRepository.findByDepartmentName(String.valueOf(row.getCell(2))).get());
                    } else {
                        return null;
                    }

                    subCategoryList.add(subCategory);
                }

            } catch (IOException e) {
                e.getStackTrace();
            }
            subCategoryRepository.saveAll(subCategoryList);
        } else {
            throw new IllegalArgumentException("This is not a valid excel file");
        }
        return subCategoryList;
    }
}