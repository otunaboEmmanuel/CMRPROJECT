package CRM.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String subCategoryName;
    @ManyToOne
    @JoinColumn(name = "subCategory_id",referencedColumnName = "categoryId")
    private Category category;
}
