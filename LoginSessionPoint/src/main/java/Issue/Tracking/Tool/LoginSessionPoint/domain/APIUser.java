package Issue.Tracking.Tool.LoginSessionPoint.domain;

import Issue.Tracking.Tool.LoginSessionPoint.constants.AbstractPersistentObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.*;

//import javax.persistence.*;

@EqualsAndHashCode(callSuper = false)

@NoArgsConstructor @AllArgsConstructor
@Entity
@Data
@Table(name = "apiuser")
public class APIUser extends  AbstractPersistentObject {

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

 //@Id
 //@GeneratedValue(strategy=GenerationType.IDENTITY)
 private Long id;

 //@Column(unique = true)
 private String username;

 private String password;

 @Column(unique = true)
 private String email;

 private String Name;


 @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
 private Collection<Role> roles = new ArrayList<>();


 @OneToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}, fetch = FetchType.LAZY)
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

 @Override
 public boolean equals(Object o) {
  if (this == o) return true;
  if (!(o instanceof APIUser)) return false;
  if (!super.equals(o)) return false;
  if(this.getUsername().equals(((APIUser) o).getUsername())) return true;
  APIUser user = (APIUser) o;
  return Objects.equals(getId(), user.getId()) && Objects.equals(getUsername(), user.getUsername()) && Objects.equals(getPassword(), user.getPassword()) && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getName(), user.getName()) && Objects.equals(getRoles(), user.getRoles()) && Objects.equals(getApiKeys(), user.getApiKeys()) && Objects.equals(getCreatedAt(), user.getCreatedAt()) && Objects.equals(getLastUpdated(), user.getLastUpdated());
 }

 @Override
 public int hashCode() {
  return Objects.hash(super.hashCode(), getId(), getUsername(), getPassword(), getEmail(), getName(), getRoles(), getApiKeys(), getCreatedAt(), getLastUpdated());
 }

 @UpdateTimestamp
 @Temporal(TemporalType.TIMESTAMP)
 private java.util.Date lastUpdated;
}