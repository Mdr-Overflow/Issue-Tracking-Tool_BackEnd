package Issue.Tracking.Tool.LoginSessionPoint.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.Nullable;


import javax.persistence.*;
import java.util.Collection;

//import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")

public class Role {

    /*@Transient
    public static final String SEQUENCE_NAME = "roles_sequence";
*/

    @Id
    // @SequenceGenerator(
    //        name = "Role_Id_seq",
    //      sequenceName = "Role_Id_seq",
    //    allocationSize = 1
    // )
    @GeneratedValue(
            strategy = GenerationType.TABLE
    )

    private Long id;

    //@Column(unique = true) Duplicate entry 'ROLE_USER' for key 'role.UK_8sewwnpamngi6b1dwaa88askk' ////
    private String name;

    /*
        @ManyToMany(
                cascade = {CascadeType.ALL} ,
                fetch = FetchType.EAGER
        )
        @JoinTable(
                name = "role_privilege",
                joinColumns = @JoinColumn(
                        name = "role_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(
                        name = "privilege_id", referencedColumnName = "id"))

        private Collection<Privilege> privileges;

    */
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date lastUpdated;
/*
    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", privileges=" + privileges.toString() +
                ", createdAt=" + createdAt +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}
*/
}