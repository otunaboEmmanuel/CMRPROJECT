package CRM.project.entity;

import javax.persistence.*;

import CRM.project.dto.CommentData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
    private LocalDateTime logTime;
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
    private String requesterUnit;

//    @ElementCollection
//    @CollectionTable(
//            name = "comment_map",
//            joinColumns = @JoinColumn(name = "id", referencedColumnName = "id")
//    )
//    @MapKeyColumn(name = "comment_key")
//    @Column(name = "comment_value")
//    @Cascade(org.hibernate.annotations.CascadeType.ALL)
//    private Map<String, String> comments;

    @ElementCollection
    @Column(name = "comment_data")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<CommentData> commentData;

    private String closureComments;
    private LocalDateTime closureTime;
    private int rating;
}
