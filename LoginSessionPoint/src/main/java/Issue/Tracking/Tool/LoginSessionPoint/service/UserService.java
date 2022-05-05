package Issue.Tracking.Tool.LoginSessionPoint.service;

import Issue.Tracking.Tool.LoginSessionPoint.domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Role;


import java.util.List;

public interface UserService {
    APIUser saveUser(APIUser user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName );
    APIUser getUser(String username);
    List<APIUser> getUsers();   //load n amount

    //List<Role> getRoles();
    List <Role> getALLRoles();
    //APIUser returnThis();
}
