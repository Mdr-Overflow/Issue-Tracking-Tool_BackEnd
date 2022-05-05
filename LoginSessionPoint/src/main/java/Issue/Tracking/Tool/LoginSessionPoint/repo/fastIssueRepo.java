package Issue.Tracking.Tool.LoginSessionPoint.repo;

import Issue.Tracking.Tool.LoginSessionPoint.fastDomain.fastIssue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface fastIssueRepo extends JpaRepository<fastIssue,Long> {
}
