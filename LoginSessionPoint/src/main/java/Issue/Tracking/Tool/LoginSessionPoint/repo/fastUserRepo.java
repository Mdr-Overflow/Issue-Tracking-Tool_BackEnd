package Issue.Tracking.Tool.LoginSessionPoint.repo;

import Issue.Tracking.Tool.LoginSessionPoint.fastDomain.fastAPIUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface fastUserRepo extends JpaRepository<fastAPIUser,Long>{
}
