package Issue.Tracking.Tool.LoginSessionPoint.domain;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_group")
public class UserGroup  implements Serializable {
    @JsonSerialize
    @JsonDeserialize

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
    @Column(name = "id")
    private Long id;

    @Column(unique = true)
    private String name;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date createdAt;


    @OneToMany(

            cascade = {CascadeType.MERGE , CascadeType.PERSIST },

            fetch = FetchType.LAZY
    )
    @JoinColumn(name= "user_group_id",referencedColumnName = "id")
    private Collection<APIUser> users;


    @OneToOne(

            cascade = {CascadeType.MERGE , CascadeType.PERSIST },

            fetch = FetchType.LAZY)

    private APIUser Leader;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date lastUpdated;

}
