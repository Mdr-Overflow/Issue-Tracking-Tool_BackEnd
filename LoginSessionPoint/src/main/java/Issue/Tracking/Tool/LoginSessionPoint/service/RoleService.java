package Issue.Tracking.Tool.LoginSessionPoint.service;

import Issue.Tracking.Tool.LoginSessionPoint.domain.Role;
import org.springframework.stereotype.Component;

import java.util.List;


public interface RoleService {
    Role saveRole(Role role);
    List<Role> getALLRoles();
    Role getRole(String name);

    Role getRoleFIX(String name);

    void deleteByName(String name);
    List<Role> findBy(String name);

    List<Role> findByTerm(String toSearch);
}
