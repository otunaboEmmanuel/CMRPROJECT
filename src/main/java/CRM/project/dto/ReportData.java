package CRM.project.dto;

import CRM.project.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportData {

    private boolean unit;
    private boolean logTime;
    private boolean subject;
    private boolean priority;
    private boolean category;
    private boolean subCategory;
    private boolean description;
    private boolean technician;
    private boolean status;
    private boolean dueDate;
    private boolean sla;
    private boolean requester;
    private boolean closureComments;
    private boolean closureTime;
}
