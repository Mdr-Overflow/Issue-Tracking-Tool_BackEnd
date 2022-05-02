package Issue.Tracking.Tool.LoginSessionPoint.Domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Priority {

    @Id
    @SequenceGenerator(
            name = "Priority_Id_seq",
            sequenceName = "Priority_Id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "Priority_Id_seq"
    )
    private Long id;

    private String name;


}
