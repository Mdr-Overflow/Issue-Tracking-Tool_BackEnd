package Issue.Tracking.Tool.LoginSessionPoint.Repo;

import Issue.Tracking.Tool.LoginSessionPoint.Domain.Group;
import Issue.Tracking.Tool.LoginSessionPoint.Domain.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface GroupRepo extends JpaRepository<Group,Long> {
   Group findByName(String name);

    @Query("SELECT g.createdAt FROM Group g where g.name = :name")
    java.util.Date findTimestampByName(String name) ;
}
