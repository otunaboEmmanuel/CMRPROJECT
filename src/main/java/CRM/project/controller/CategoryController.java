package CRM.project.controller;

import CRM.project.entity.Category;
import CRM.project.entity.Department;
import CRM.project.response.Responses;
import CRM.project.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@CrossOrigin
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/addCategory")
    public ResponseEntity<?> addNewCategory(@RequestBody Category category)
    {
        log.info("Incoming request is::: "+category.toString());
        Category category1=categoryService.addNewCategory(category);
        return category1==null? new ResponseEntity<>(new Responses("00", "category details Saved Successfully"), HttpStatus.OK)
                : new ResponseEntity<>(new Responses("99", "Record not saved, Ensure category name does not exist"), HttpStatus.OK);
    }
    @PostMapping("/allCategory")
    public ResponseEntity<?> getAllProducts(@RequestBody Category category){
        log.info("Getting all categories "+category.toString());
        List<Category> categories=categoryService.getAllCategory(category);
        return ResponseEntity.ok(categories);
    }

}
