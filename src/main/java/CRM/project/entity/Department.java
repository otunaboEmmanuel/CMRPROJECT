package CRM.project.entity;

import javax.persistence.*;

import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department extends TimeClass {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long departmentId;
   private String departmentName;
//    @OneToMany
//    @JoinColumn(name = "users_id", referencedColumnName = "unitId")
    private String email;
    @Transient
    private String creationType;
}
