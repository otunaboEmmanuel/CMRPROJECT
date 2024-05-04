package CRM.project.service;

import CRM.project.entity.Category;
import CRM.project.entity.Department;
import CRM.project.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public Category addNewCategory(Category category) {
        Category category1=categoryRepository.findByCategoryName(category.getCategoryName()).orElse(null);
        return category1== null ? categoryRepository.save(category) : null;
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }
}
