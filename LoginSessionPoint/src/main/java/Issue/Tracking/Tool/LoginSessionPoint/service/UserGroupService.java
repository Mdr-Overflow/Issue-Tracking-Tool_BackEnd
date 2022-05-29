package Issue.Tracking.Tool.LoginSessionPoint.service;

import Issue.Tracking.Tool.LoginSessionPoint.domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.domain.UserGroup;

import java.util.List;

public interface UserGroupService {
    UserGroup saveGroup(UserGroup userGroup);
    UserGroup getGroup(String groupName);
    List<UserGroup> getGroups();
    List<APIUser> getUsers(String name);
    APIUser getLeader(String name);


    void AddUserToGroup(String username, String groupName);
    void deleteByName(String name);

    java.util.Date getTimestamp(String IssueName);


    List<UserGroup> findBy(String toSearch);

    List<UserGroup> findByLeader(String toSearch);

    UserGroup  findByUsernameOFUser(String username);
}
