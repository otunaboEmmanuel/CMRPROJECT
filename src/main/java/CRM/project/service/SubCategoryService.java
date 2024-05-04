package CRM.project.service;

import CRM.project.entity.Category;
import CRM.project.entity.SubCategory;

import java.util.List;

public interface SubCategoryService {
    List<SubCategory> getSubcategoriesByCategory(Category category1);
}
