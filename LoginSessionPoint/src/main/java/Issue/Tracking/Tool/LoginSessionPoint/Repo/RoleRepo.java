package Issue.Tracking.Tool.LoginSessionPoint.Repo;

import Issue.Tracking.Tool.LoginSessionPoint.Domain.Role;
;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


public interface RoleRepo  extends JpaRepository<Role,Long> {
    Role findByName(String name);


}