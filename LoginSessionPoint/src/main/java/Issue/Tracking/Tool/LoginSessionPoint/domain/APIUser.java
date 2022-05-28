package Issue.Tracking.Tool.LoginSessionPoint.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

//import javax.persistence.*;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
@Table(name = "apiuser")
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

 @Column(name = "id")
 private Long id;

 @Column(unique = true)
 private String username;

 private String password;

 @Column(unique = true)
 private String email;

 private String Name;

 @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
 private Collection<Role> roles = new ArrayList<>();


 @OneToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.DETACH}, fetch = FetchType.LAZY, orphanRemoval = true)
 private Collection<apiKeyPair> apiKeys = new ArrayList<apiKeyPair>();

 /*
 @ManyToOne(fetch = FetchType.LAZY)
 private UserGroup userGroup;

 @OneToOne(fetch = FetchType.LAZY)
 private UserGroup userGroupLeading;
*/
 @CreationTimestamp
 @Temporal(TemporalType.TIMESTAMP)
 private java.util.Date createdAt;

 @UpdateTimestamp
 @Temporal(TemporalType.TIMESTAMP)
 private java.util.Date lastUpdated;
}