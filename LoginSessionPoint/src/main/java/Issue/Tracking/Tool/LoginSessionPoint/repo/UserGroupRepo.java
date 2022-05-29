package Issue.Tracking.Tool.LoginSessionPoint.repo;

import Issue.Tracking.Tool.LoginSessionPoint.domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Issue;
import Issue.Tracking.Tool.LoginSessionPoint.domain.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserGroupRepo extends JpaRepository<UserGroup,Long> {
   UserGroup findByName(String name);


    @Query("SELECT g.createdAt FROM UserGroup g where g.name = :name")
    java.util.Date findTimestampByName(String name) ;

    @Query("SELECT g.users FROM UserGroup g where g.name = :name")
    List<APIUser> getALLUsers(String name);

    @Query("SELECT g.Leader FROM UserGroup g where g.name = :name")
    APIUser findLeader(String name);

    void deleteByName(String name);



 @Query(value = " SELECT DISTINCT g FROM UserGroup as g join g.Leader l join g.users us WHERE (:inputString is null or g.id = :inputInt) or (:inputString is null or l.username like :inputString) " +
         "or  (:inputString is null or g.name like " + ":inputString)  or (:inputString is null or us.username like :inputString)"
 )
    List<UserGroup> findBy(String inputString, Long inputInt);


    @Query(value = "select distinct g from UserGroup g\n" +
            "    join g.Leader u \n" +
            "    where u.Name like :toSearch\n"
    )

    List<UserGroup> findByLeader_NameContains(String toSearch);


    @Query("FROM UserGroup g join g.users us WHERE us.username = :username ")
    UserGroup findByUsernameOFUser(String username);
}
