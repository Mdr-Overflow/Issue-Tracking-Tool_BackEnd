package Issue.Tracking.Tool.LoginSessionPoint.domain;

import Issue.Tracking.Tool.LoginSessionPoint.constants.AbstractPersistentObject;
import Issue.Tracking.Tool.LoginSessionPoint.constants.UserDeserializer;
import Issue.Tracking.Tool.LoginSessionPoint.constants.UserSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
@JsonSerialize(using = UserSerializer.class)

public class APIUser extends  AbstractPersistentObject {


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

 @Column(unique = true)
 private String email;

 private String Name;



 @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
 private Collection<Role> roles = new ArrayList<>();


 @OneToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}, fetch = FetchType.LAZY)
 private Collection<apiKeyPair> apiKeys = new ArrayList<apiKeyPair>();


 @CreationTimestamp
 @Temporal(TemporalType.TIMESTAMP)
 @JsonFormat
         (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
 private java.util.Date createdAt;

 @Override
 public String toString() {
  return "APIUser{" +
          "id=" + id +
          ", username='" + username + '\'' +
          ", password='" + password + '\'' +
          ", email='" + email + '\'' +
          ", Name='" + Name + '\'' +
          ", roles=" + roles.toString() +
          ", apiKeys=" + apiKeys +
          ", createdAt=" + createdAt +
          ", lastUpdated=" + lastUpdated +
          '}';
 }

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
 @JsonFormat
         (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
 private java.util.Date lastUpdated;
}