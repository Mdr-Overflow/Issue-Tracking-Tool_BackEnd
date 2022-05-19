package Issue.Tracking.Tool.LoginSessionPoint.service;

import Issue.Tracking.Tool.LoginSessionPoint.domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.domain.UserGroup;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Issue;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Solution;

import java.util.List;

public interface IssueService {

    Issue saveIssue(Issue issue);
    Issue getIssue(String IssueName);
    List<Issue> getIssues();

    //Status , priority , details

    List<Solution> getSols(Issue issue);
    List<APIUser> getContributors(Issue issue);
    List<UserGroup> getGroups(Issue issue);

    void AddSol(Solution solution, String issueName);
    void AddUser(APIUser user, String issueName);
    void AddGroup(UserGroup userGroup, String issueName);

    void deleteByName(String name);

    java.util.Date getTimestamp(String IssueName);


}
