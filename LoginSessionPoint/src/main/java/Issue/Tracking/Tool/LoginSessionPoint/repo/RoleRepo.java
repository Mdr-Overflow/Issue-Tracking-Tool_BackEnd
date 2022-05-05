package Issue.Tracking.Tool.LoginSessionPoint.repo;

import Issue.Tracking.Tool.LoginSessionPoint.domain.Role;
;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepo  extends JpaRepository<Role,Long> {
    Role findByName(String name);


}