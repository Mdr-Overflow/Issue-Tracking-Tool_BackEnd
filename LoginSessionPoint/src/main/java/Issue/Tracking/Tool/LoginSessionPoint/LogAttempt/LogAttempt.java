/*package Issue.Tracking.Tool.LoginSessionPoint.LogAttempt;


import javax.persistence.*;

@Entity
@Table
public class LogAttempt {
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
   private String Username;
   private String Password;
   private Boolean Create;


    public LogAttempt() {
    }

    public LogAttempt(String username,
                      String password,
                      Boolean create) {
        Username = username;
        Password = password;
        Create = create;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public Boolean getCreate() {
        return Create;
    }

    public void setCreate(Boolean create) {
        Create = create;
    }

    @Override
    public String toString() {
        return "LogAttempt{" +
                "Username='" + Username + '\'' +
                ", Password='" + Password + '\'' +
                ", Create=" + Create +
                '}';
    }
}
*/