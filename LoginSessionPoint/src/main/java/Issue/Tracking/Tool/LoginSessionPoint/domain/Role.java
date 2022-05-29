package Issue.Tracking.Tool.LoginSessionPoint.domain;

import Issue.Tracking.Tool.LoginSessionPoint.constants.RoleSerializer;
import Issue.Tracking.Tool.LoginSessionPoint.constants.UserSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.Nullable;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

import static org.hibernate.annotations.FetchMode.JOIN;

//import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
@JsonSerialize(using = RoleSerializer.class)
public class Role  {

    /*@Transient
    public static final String SEQUENCE_NAME = "roles_sequence";
*/

    @Id
    @SequenceGenerator(
            name = "Role_Id_seq",
            sequenceName = "Role_Id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "Role_Id_seq"
    )

    private Long id;

    //@Column(unique = true) Duplicate entry 'ROLE_USER' for key 'role.UK_8sewwnpamngi6b1dwaa88askk' ////
    private String name;


        @ManyToMany(
                cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH} ,
                fetch = FetchType.EAGER
        )
        @Fetch(JOIN)
        @JoinTable(
                name = "role_privilege",
                joinColumns = @JoinColumn(
                        name = "role_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(
                        name = "privilege_id", referencedColumnName = "id"))

        private Collection<Privilege> privileges;


    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private java.util.Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private java.util.Date lastUpdated;

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", privileges=" + privileges +
                ", createdAt=" + createdAt +
                ", lastUpdated=" + lastUpdated +
                '}';
    }


}

