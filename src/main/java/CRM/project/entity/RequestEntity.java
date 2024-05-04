package CRM.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "REQUEST")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Integer Id;
    private String unit;
    private String subject;
    private String priority;
    private String category;
    private String subCategory;
    private String description;
    private String Technician;
    private String name;
    private String type;
    private String filePath;
}
