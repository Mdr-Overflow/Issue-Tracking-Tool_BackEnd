package Issue.Tracking.Tool.LoginSessionPoint.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Type {



    @Id
    @SequenceGenerator(
            name = "Type_Id_seq",
            sequenceName = "Type_Id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "Type_Id_seq"
    )
    private Long id;

    @Column(unique = true)
    private String name;


    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date createdAt;


    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date lastUpdated;


}
