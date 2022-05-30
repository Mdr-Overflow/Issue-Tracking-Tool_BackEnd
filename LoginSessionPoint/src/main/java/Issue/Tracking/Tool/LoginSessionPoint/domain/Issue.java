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
@NoArgsConstructor
@AllArgsConstructor
public class Issue {

    @Id
    @SequenceGenerator(
            name = "Issue_Id_seq",
            sequenceName = "Issue_Id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "Issue_Id_seq"
    )
    private Long id;
    @Column(unique = true)
    private String name;

    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true)
    private Status status;


    private String details;

    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true)
    private Priority priority;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date lastUpdated;

    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE , CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH  })
    private Collection <Solution> solutions = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE , CascadeType.PERSIST , CascadeType.DETACH,CascadeType.REFRESH})
    private Collection <UserGroup> userGroups = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE , CascadeType.PERSIST, CascadeType.DETACH,CascadeType.REFRESH })
    private Collection <APIUser> users = new ArrayList<>();
}
