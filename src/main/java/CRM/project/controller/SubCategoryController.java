package CRM.project.controller;

import CRM.project.dto.Requestdto;
import CRM.project.entity.Category;
import CRM.project.entity.Department;
import CRM.project.entity.SubCategory;
import CRM.project.entity.Users;
import CRM.project.repository.CategoryRepository;
import CRM.project.response.Responses;
import CRM.project.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/subcategories")
public class SubCategoryController {
    @Autowired
    private SubCategoryService subCategoryService;
    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("/getSubCategoryByCategory")
    public List<SubCategory> getSubCategory(@RequestBody Requestdto category) {

        Category category1 = categoryRepository.findByCategoryName(category.getCategoryName()).orElse(null);
        if (category1 != null) {
            return subCategoryService.getSubcategoriesByCategory(category1);
        } else
            return null;
    }
}
