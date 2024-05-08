package CRM.project.dto;

import CRM.project.entity.RequestEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class BulkDto {

    private ArrayList<RequestEntity> requests;
    private String technician;
}
