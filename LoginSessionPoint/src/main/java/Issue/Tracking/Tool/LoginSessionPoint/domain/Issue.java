package Issue.Tracking.Tool.LoginSessionPoint.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
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

    @OneToOne(fetch = FetchType.EAGER,cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.DETACH})
    //@Fetch( FetchMode.SELECT)
    @Nullable
   // @JoinColumn(name= "issue_id",referencedColumnName = "id", insertable = true, updatable = true)
    private Status status;


    private String details;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.DETACH})
    @Nullable
   // @Fetch( FetchMode.SELECT)
  //  @JoinColumn(name= "issue_id2",referencedColumnName = "id", insertable = true, updatable = true)
    private Priority priority;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date lastUpdated;

    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date DueDate;

    @OneToMany(fetch = FetchType.EAGER,cascade = {CascadeType.MERGE , CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH  })
    @Nullable
    private Collection <Solution> solutions = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE , CascadeType.PERSIST , CascadeType.DETACH,CascadeType.REFRESH})
    @Nullable
    private Collection <UserGroup> userGroups = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE , CascadeType.PERSIST, CascadeType.DETACH,CascadeType.REFRESH })
    @Nullable
    private Collection <APIUser> users = new ArrayList<>();
}
