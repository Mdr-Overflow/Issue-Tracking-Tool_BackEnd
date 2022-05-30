package Issue.Tracking.Tool.LoginSessionPoint.repo;

import Issue.Tracking.Tool.LoginSessionPoint.domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.domain.UserGroup;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Issue;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface IssueRepo extends JpaRepository <Issue,Long>{
    Issue findByName(String name);

    @Query("SELECT i.solutions FROM Issue i where i.name = :name")
    List<Solution> findAllSolsByName(@Param("name") String name);

    @Query("SELECT i.users FROM Issue i where i.name = :name")
    List<APIUser> findAllUsersByName(@Param("name") String name);

    @Query("SELECT i.userGroups FROM Issue i where i.name = :name")
    List<UserGroup> findAllGroupsByName(@Param("name") String name);

    @Query("SELECT i.createdAt FROM Issue i where i.name = :name")
    java.util.Date findTimestampByName(String name) ;


    @Query(value = "SELECT DISTINCT i FROM Issue as i join i.priority p join i.status s " +
            "WHERE (:inputString is null or i.id = :inputInt) or (:inputString is null or p.name like :inputString)" +
            " and (:inputString is null or s.name like :inputString) and " +
            " (:inputString is null or i.name like " + ":inputString" + " ) or " +
            "(:inputString is null or i.details  like "  + ":inputString" + " )  or (:inputDate is not null ) "
    )
    List<Issue> findBy(String inputString, Long inputInt, Integer inputDate);



    @Query(value = "FROM Issue as i join i.priority p WHERE p.name like :name")
    List<Issue> findAllByPriority_NameContains(String name);

    @Query(value = "FROM Issue as i join i.status p WHERE p.name like :name")
    List<Issue> findAllByStatus_NameContains(String name);

    void deleteByName(String name);

    @Query(value = "SELECT  i FROM Issue as i join i.users iu WHERE iu.username = :name ")
    List<Issue> findByNameOfUsers(String name);
}
