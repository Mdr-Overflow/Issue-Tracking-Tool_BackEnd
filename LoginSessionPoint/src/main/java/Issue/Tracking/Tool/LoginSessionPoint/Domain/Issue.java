package Issue.Tracking.Tool.LoginSessionPoint.Domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    private Status status;


    private String details;

    @OneToOne(fetch = FetchType.LAZY)
    private Priority priority;

    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date createdAt;

    @OneToMany(fetch = FetchType.LAZY)
    private Collection <Solution> solutions = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    private Collection <Group> groups = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    private Collection <APIUser> users = new ArrayList<>();
}
