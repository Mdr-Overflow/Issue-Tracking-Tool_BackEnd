package Issue.Tracking.Tool.LoginSessionPoint.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

//import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Data @NoArgsConstructor @AllArgsConstructor @Document(collection = "User")
public class APIUser {

    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";


    @Id
  /* @SequenceGenerator(
            name = "User_Id_seq",
            sequenceName = "User_Id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "User_Id_seq"
    )
    */

    private Long id;
    private String username;
    private String password;
    //@ManyToMany(fetch = FetchType.EAGER
    @DBRef(lazy = true)  // for 'artificial' persistance
    private Collection<Role> roles = new ArrayList<>();


}
