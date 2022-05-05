package Issue.Tracking.Tool.LoginSessionPoint.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserGroup {
    @Id
    @SequenceGenerator(
            name = "Group_Id_seq",
            sequenceName = "Group_Id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "Group_Id_seq"
    )
    private Long id;
    private String name;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date createdAt;
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Collection<APIUser> users = new ArrayList<>();


    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date lastUpdated;

}
