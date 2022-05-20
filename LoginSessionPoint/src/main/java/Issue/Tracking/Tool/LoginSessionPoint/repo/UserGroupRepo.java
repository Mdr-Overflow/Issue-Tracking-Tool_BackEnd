package Issue.Tracking.Tool.LoginSessionPoint.repo;

import Issue.Tracking.Tool.LoginSessionPoint.domain.APIUser;
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
}
