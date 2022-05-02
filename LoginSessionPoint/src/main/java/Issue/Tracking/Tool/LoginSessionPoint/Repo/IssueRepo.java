package Issue.Tracking.Tool.LoginSessionPoint.Repo;

import Issue.Tracking.Tool.LoginSessionPoint.Domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.Domain.Group;
import Issue.Tracking.Tool.LoginSessionPoint.Domain.Issue;
import Issue.Tracking.Tool.LoginSessionPoint.Domain.Solution;
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

    @Query("SELECT i.groups FROM Issue i where i.name = :name")
    List<Group> findAllGroupsByName(@Param("name") String name);

    @Query("SELECT i.createdAt FROM Issue i where i.name = :name")
    java.util.Date findTimestampByName(String name) ;
}
