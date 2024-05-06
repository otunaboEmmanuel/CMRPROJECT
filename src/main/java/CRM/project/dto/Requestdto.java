package CRM.project.dto;

import CRM.project.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Requestdto {
    private Integer Id;
    private String unit;
    private String subject;
    private String priority;
    private String category;
    private String subCategory;
    private String description;
    private String Technician;
    private Long departmentId;
    private String departmentName;
    private Users user;
    private String status;
    private Long unitId;
    private String unitName;
    private String staffName;
    private long categoryId;
    private String categoryName;
}
