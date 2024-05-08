package CRM.project.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;



@MappedSuperclass
public abstract class TimeClass {

    @CreatedDate
    @Column(name = "created_date", insertable = true, updatable = false)
    private LocalDateTime createdTime;

    @LastModifiedDate
    @Column(name = "last_modified_date", updatable = true, insertable = false)
    private LocalDateTime modifiedTime;
}
