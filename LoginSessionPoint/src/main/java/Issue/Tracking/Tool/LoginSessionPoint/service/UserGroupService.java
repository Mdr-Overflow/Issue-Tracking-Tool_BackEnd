package Issue.Tracking.Tool.LoginSessionPoint.service;

import Issue.Tracking.Tool.LoginSessionPoint.domain.UserGroup;

import java.util.List;

public interface UserGroupService {
    UserGroup saveGroup(UserGroup userGroup);
    UserGroup getGroup(String groupName);
    List<UserGroup> getGroups();

    void AddUserToGroup(String username, String groupName);
    void deleteByName(String name);

    java.util.Date getTimestamp(String IssueName);


}