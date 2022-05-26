package Issue.Tracking.Tool.LoginSessionPoint.service;

import Issue.Tracking.Tool.LoginSessionPoint.domain.Role;

import java.util.List;

public interface RoleService {
    Role saveRole(Role role);
    List<Role> getALLRoles();
    Role getRole(String name);
    void deleteByName(String name);
    List<Role> findBy(String name);
}
