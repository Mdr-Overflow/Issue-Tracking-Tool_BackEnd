package Issue.Tracking.Tool.LoginSessionPoint.Repo;

import Issue.Tracking.Tool.LoginSessionPoint.Domain.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserGroupRepo extends JpaRepository<UserGroup,Long> {
   UserGroup findByName(String name);

    @Query("SELECT g.createdAt FROM UserGroup g where g.name = :name")
    java.util.Date findTimestampByName(String name) ;
}
