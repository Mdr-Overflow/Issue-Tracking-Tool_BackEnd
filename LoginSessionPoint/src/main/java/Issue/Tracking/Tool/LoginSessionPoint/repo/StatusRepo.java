package Issue.Tracking.Tool.LoginSessionPoint.repo;

import Issue.Tracking.Tool.LoginSessionPoint.domain.Priority;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepo extends JpaRepository<Status,Long> {

    Status getStatusByName(String name);


    void deleteByName(String name);

}
