package Issue.Tracking.Tool.LoginSessionPoint.Repo;

import Issue.Tracking.Tool.LoginSessionPoint.Domain.APIUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo  extends JpaRepository<APIUser,Long> {
    APIUser findByUsername(String username);

}
