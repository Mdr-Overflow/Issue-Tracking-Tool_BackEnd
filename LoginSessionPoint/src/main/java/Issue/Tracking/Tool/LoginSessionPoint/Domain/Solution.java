package Issue.Tracking.Tool.LoginSessionPoint.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Solution {

    @Id
    @SequenceGenerator(
            name = "Solution_Id_seq",
            sequenceName = "Solution_Id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "Solution_Id_seq"
    )
    private Long id;
    private String name;
    private String description;
    private boolean isFinal;
    private boolean isAccepted;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Type type;

    private String content;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date lastUpdated;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private APIUser owner;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Collection <APIUser> collaborators ;








}
