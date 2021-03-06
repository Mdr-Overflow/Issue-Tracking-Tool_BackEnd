package Issue.Tracking.Tool.LoginSessionPoint.service;

import Issue.Tracking.Tool.LoginSessionPoint.domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Role;
import io.swagger.models.auth.In;


import java.util.List;

public interface UserService {
    APIUser saveUser(APIUser user);

    void addRoleToUser(String username, String roleName );
    APIUser getUser(String username);

    List<APIUser> getUsers(Integer PageIndex, Integer PageSize, String SortBy, Integer SortDirection);
    List<APIUser> getUsersALL();
    List<String> getAllUsernames();

    void deleteByUsername(String username);

    List<APIUser> findBy(String toSearch);

    List<APIUser> getUsersNOGROUP();

}
