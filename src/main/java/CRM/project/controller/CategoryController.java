package CRM.project.controller;

import CRM.project.entity.Category;
import CRM.project.entity.Department;
import CRM.project.response.Responses;
import CRM.project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping("/addCategory")
    public ResponseEntity<?> addNewCategory(@RequestBody Category category)
    {
        Category category1=categoryService.addNewCategory(category);
        return category1==null? new ResponseEntity<>(new Responses("00", "category details Saved Successfully"), HttpStatus.OK)
                : new ResponseEntity<>(new Responses("99", "Record not saved, Ensure category name does not exist"), HttpStatus.OK);
    }
    @PostMapping("/allCategory")
    public ResponseEntity<?> getAllProducts(@RequestBody Category category){
        List<Category> categories=categoryService.getAllCategory();
        return ResponseEntity.ok(categories);
    }

}
