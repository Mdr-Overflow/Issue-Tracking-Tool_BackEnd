package Issue.Tracking.Tool.LoginSessionPoint.repo;

import Issue.Tracking.Tool.LoginSessionPoint.domain.APIUser;

import Issue.Tracking.Tool.LoginSessionPoint.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo  extends JpaRepository<APIUser,Long> {
    APIUser findByUsername(String username);
    List<Role> findAllByUsername(String username);
    void deleteByUsername(String username);

 }
