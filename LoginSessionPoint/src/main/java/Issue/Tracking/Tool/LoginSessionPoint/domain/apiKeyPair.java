package Issue.Tracking.Tool.LoginSessionPoint.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class apiKeyPair {
    @Id
    @SequenceGenerator(
            name = "apiKeyPair_Id_seq",
            sequenceName = "apiKeyPair_Id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "apiKeyPair_Id_seq"
    )
    private long id;//PK
    private String ApiKey;
    private String SecretKey;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date lastUpdated;

}
