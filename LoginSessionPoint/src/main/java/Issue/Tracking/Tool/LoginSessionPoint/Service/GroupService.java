package Issue.Tracking.Tool.LoginSessionPoint.Service;

import Issue.Tracking.Tool.LoginSessionPoint.Domain.Group;
import Issue.Tracking.Tool.LoginSessionPoint.Domain.Issue;

import java.util.List;

public interface GroupService {
    Group saveGroup(Group group);
    Group getGroup(String groupName);
    List<Group> getGroups();

    void AddUserToGroup(String username, String groupName);


    java.util.Date getTimestamp(String IssueName);


}
