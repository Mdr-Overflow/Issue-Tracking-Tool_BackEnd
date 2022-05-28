package Issue.Tracking.Tool.LoginSessionPoint.repo;

import Issue.Tracking.Tool.LoginSessionPoint.domain.Role;
;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface RoleRepo  extends JpaRepository<Role,Long> {
    Role findByName(String name);
    void deleteByName(String name);
    void removeByName(String name);

   List<Role> findByNameContaining(String toSearch);


}