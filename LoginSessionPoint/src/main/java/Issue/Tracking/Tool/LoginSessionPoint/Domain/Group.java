package Issue.Tracking.Tool.LoginSessionPoint.Domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Group {
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
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date createdAt;
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<APIUser> users = new ArrayList<>();

}
