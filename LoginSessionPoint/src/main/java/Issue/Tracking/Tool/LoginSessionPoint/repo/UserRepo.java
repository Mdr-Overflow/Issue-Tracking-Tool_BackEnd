package Issue.Tracking.Tool.LoginSessionPoint.repo;

import Issue.Tracking.Tool.LoginSessionPoint.domain.APIUser;

import Issue.Tracking.Tool.LoginSessionPoint.domain.Role;
import Issue.Tracking.Tool.LoginSessionPoint.domain.apiKeyPair;
import Issue.Tracking.Tool.LoginSessionPoint.service.RoleService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

public interface UserRepo  extends JpaRepository<APIUser,Long> {
    APIUser findByUsername(String username);
    List<Role> findAllByUsername(String username);
    void deleteByUsername(String username);

 /*   @Modifying
    @Query(value = "update APIUser u set u.username = ?2, u.createdAt = ?3, u.apiKeys = ?4 , u.email = ?5 , u.lastUpdated = ?6, u.Name = ?7, u.password = ?8, u.roles = ?9 where u.username = ?1")
    void UpdateUser(String username, String newUsername, Date createdAt,
                    List<apiKeyPair> apiKeys, String email , Date lastUpdated, String Name, String password ,
                    List<Role> roles);
                    */

 }
