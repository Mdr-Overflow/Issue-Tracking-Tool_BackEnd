package Issue.Tracking.Tool.LoginSessionPoint.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


//import javax.persistence.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor

public class APIUser {

   /* @Transient
    public static final String SEQUENCE_NAME = "users_sequence";
*/

    @Id
   @SequenceGenerator(
            name = "User_Id_seq",
            sequenceName = "User_Id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "User_Id_seq"
    )


    private Long id;
    private String username;
    private String password;
    private String email;
    private String Name;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Collection <Role> roles = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Collection <apiKeyPair> apiKeys=new ArrayList();

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date lastUpdated;
}
