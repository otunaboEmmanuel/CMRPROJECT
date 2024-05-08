package CRM.project.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Users extends TimeClass {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long unitId;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department unitName;
    private String staffName;
    private String userEmail;
}
