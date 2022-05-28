package Issue.Tracking.Tool.LoginSessionPoint.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "privilege")
public class Privilege {
    @Id

    @SequenceGenerator(
            name = "Privilege_Id_seq",
            sequenceName = "Privilege_Id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "Privilege_Id_seq"
    )

    @Column(name = "id")
    private Long id;

    private String name;

  //  @ManyToMany(mappedBy = "privileges")
   // private Collection<Role> roles;
}
