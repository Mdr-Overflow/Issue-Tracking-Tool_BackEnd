package Issue.Tracking.Tool.LoginSessionPoint.Repo;

import Issue.Tracking.Tool.LoginSessionPoint.Domain.APIUser;

import Issue.Tracking.Tool.LoginSessionPoint.Domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserRepo  extends JpaRepository<APIUser,Long> {
    APIUser findByUsername(String username);
    List<Role> findAllByUsername(String username);

}
