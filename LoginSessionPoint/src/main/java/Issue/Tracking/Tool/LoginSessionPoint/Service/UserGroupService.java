package Issue.Tracking.Tool.LoginSessionPoint.Service;

import Issue.Tracking.Tool.LoginSessionPoint.Domain.UserGroup;

import java.util.List;

public interface UserGroupService {
    UserGroup saveGroup(UserGroup userGroup);
    UserGroup getGroup(String groupName);
    List<UserGroup> getGroups();

    void AddUserToGroup(String username, String groupName);


    java.util.Date getTimestamp(String IssueName);


}
