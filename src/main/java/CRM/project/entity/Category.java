package CRM.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long categoryId;
    private String categoryName;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department unitName;

    @Transient
    private String department;
//    @OneToMany
//    private List<SubCategory> subCategories;
}
