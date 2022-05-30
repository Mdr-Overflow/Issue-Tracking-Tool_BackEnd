package Issue.Tracking.Tool.LoginSessionPoint.repo;

import Issue.Tracking.Tool.LoginSessionPoint.domain.APIUser;

import Issue.Tracking.Tool.LoginSessionPoint.domain.Role;
import Issue.Tracking.Tool.LoginSessionPoint.domain.apiKeyPair;
import Issue.Tracking.Tool.LoginSessionPoint.service.RoleService;
import lombok.Synchronized;
import org.hibernate.annotations.Synchronize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.PreRemove;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

public interface UserRepo  extends JpaRepository<APIUser,Long> , JpaSpecificationExecutor<APIUser>
{



    APIUser findFirstByUsername(String name);


    List<Role> findAllByUsername(String username);

//FetchMode.SELECT



   // void DelDupes();
   // @Query("DELETE u from APIUser u")
    void deleteByUsername(String username);

    @Query("SELECT u.username FROM APIUser u ")
    List<String> getALLUsernames();

    List<APIUser> findByUsernameContains(String usernameWILD);

    @Query(value = "SELECT DISTINCT u FROM APIUser as u join u.roles r  WHERE (:inputString is null or u.id = :inputInt)  or (:inputString is null or r.name like :inputString ) or  (:inputString is null or u.username like " + ":inputString" + " ) or " +
            "(:inputString is null or u.Name  like "  + ":inputString" + " ) or " +
            " (:inputString is null or u.email  like "  + ":inputString"+")"
    )
    List<APIUser> findBy(String inputString,Long inputInt);

    @Query(value = "SELECT DISTINCT u FROM APIUser as u join u.roles r  WHERE :name = r.name ")
    List<APIUser> findAllByRoles_Name(String name);


 /*   @Modifying
    @Query(value = "update APIUser u set u.username = ?2, u.createdAt = ?3, u.apiKeys = ?4 , u.email = ?5 , u.lastUpdated = ?6, u.Name = ?7, u.password = ?8, u.roles = ?9 where u.username = ?1")
    void UpdateUser(String username, String newUsername, Date createdAt,
                    List<apiKeyPair> apiKeys, String email , Date lastUpdated, String Name, String password ,
                    List<Role> roles);
                    */

 }
