package Issue.Tracking.Tool.LoginSessionPoint.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;


import javax.persistence.*;
import java.io.Serializable;

//import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor


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
    private String name;
}
