package CRM.project.repository;

import CRM.project.entity.Category;
import CRM.project.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory,Long> {
    List<SubCategory> findByCategory(Category category1);
}
