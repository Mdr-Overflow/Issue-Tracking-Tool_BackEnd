package Issue.Tracking.Tool.LoginSessionPoint.repo;

import Issue.Tracking.Tool.LoginSessionPoint.domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.domain.UserGroup;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Issue;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    void deleteByName(String name);
}
