package CRM.project.service;

import CRM.project.entity.Category;
import CRM.project.entity.Department;
import CRM.project.repository.CategoryRepository;
import CRM.project.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DepartmentRepository departmentRepository;
    @Override
    public Category addNewCategory(Category category) {
        Department department1=departmentRepository.findByDepartmentName(category.getDepartment()).orElse(null);

        if(department1 != null) {
            Category category1 = categoryRepository.findByCategoryName(category.getCategoryName()).orElse(null);
            return category1 == null ? categoryRepository.save(category) : null;
        }
        return null;
    }

    @Override
    public List<Category> getAllCategory(Category category) {
        Department department1=departmentRepository.findByDepartmentName(category.getDepartment()).orElse(null);
        return department1!=null? categoryRepository.findByUnitName(department1): null;
    }
}
