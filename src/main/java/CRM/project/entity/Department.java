package CRM.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long departmentId;
   private String departmentName;

    @ManyToOne
    @JoinColumn(name = "users_id", referencedColumnName = "unitId")
    private Users user;

}
