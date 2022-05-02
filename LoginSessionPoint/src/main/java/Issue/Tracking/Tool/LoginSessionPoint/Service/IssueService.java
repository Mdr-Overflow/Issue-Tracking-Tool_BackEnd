package Issue.Tracking.Tool.LoginSessionPoint.Service;

import Issue.Tracking.Tool.LoginSessionPoint.Domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.Domain.Group;
import Issue.Tracking.Tool.LoginSessionPoint.Domain.Issue;
import Issue.Tracking.Tool.LoginSessionPoint.Domain.Solution;

import java.util.List;

public interface IssueService {

    Issue saveIssue(Issue issue);
    Issue getIssue(String IssueName);
    List<Issue> getIssues();

    //Status , priority , details

    List<Solution> getSols(Issue issue);
    List<APIUser> getContributors(Issue issue);
    List<Group> getGroups(Issue issue);

    void AddSol(Solution solution, String issueName);
    void AddUser(APIUser user, String issueName);
    void AddGroup(Group group, String issueName);


    java.util.Date getTimestamp(String IssueName);


}
