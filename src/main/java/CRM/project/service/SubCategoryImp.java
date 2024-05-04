package CRM.project.service;

import CRM.project.entity.Category;
import CRM.project.entity.SubCategory;
import CRM.project.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubCategoryImp implements SubCategoryService {
    @Autowired
    private SubCategoryRepository subCategoryRepository;
    @Override
    public List<SubCategory> getSubcategoriesByCategory(Category category1) {
        return subCategoryRepository.findByCategory(category1);
    }
}
