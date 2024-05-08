package CRM.project.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "REQUEST")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class RequestEntity extends TimeClass {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Integer id;
    private String unit;
    private String subject;
    private String priority;
    private String category;
    private String subCategory;
    private String description;
    private String technician;
    private String name;
    private String type;
    private String email;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String filePath;
    private LocalDateTime dueDate;
    private int sla;
    private String requester;
    private String closureComments;
    private LocalDateTime closureTime;
}
