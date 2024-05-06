package CRM.project.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubCategory extends TimeClass {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String subCategoryName;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    private int sla;

    @ManyToOne
    @JoinColumn(name = "subCategory_id",referencedColumnName = "categoryId")
    private Category category;
}
