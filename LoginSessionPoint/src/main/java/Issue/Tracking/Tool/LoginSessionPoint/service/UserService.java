package Issue.Tracking.Tool.LoginSessionPoint.service;

import Issue.Tracking.Tool.LoginSessionPoint.domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Role;


import java.util.List;

public interface UserService {
    APIUser saveUser(APIUser user);

    void addRoleToUser(String username, String roleName );
    APIUser getUser(String username);
    List<APIUser> getUsers();   //load n amount
    //void updateUser(APIUser userOld, APIUser userNew);


    //List<Role> getRoles();



    void deleteByUsername(String username);
    //APIUser returnThis();
}
