package Issue.Tracking.Tool.LoginSessionPoint.Service;

import Issue.Tracking.Tool.LoginSessionPoint.Domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.Domain.Role;

import java.util.List;

public interface UserService {
    APIUser saveUser(APIUser user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName );
    APIUser getUser(String username);
    List<APIUser> getUsers();   //load n amount
}
