package Issue.Tracking.Tool.LoginSessionPoint.service;

import Issue.Tracking.Tool.LoginSessionPoint.domain.*;

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

    void AddPriority(Priority priority,String name);
    void AddStatus(Status status, String name);

    void deleteByName(String name);

    java.util.Date getTimestamp(String IssueName);


    String getDetails(Issue issue);

    Status getStatus(Issue issue);

    Priority getPrio(Issue issue);

    List<Issue> findBy(String toSearch);



    List<Issue> findByPrio(String toSearch);

    List<Issue> findByStatus(String toSearch);

  //  List<Issue> findByProp(String prop, String name);

    List<Issue> findByNameOfUsers(String name);

     List<APIUser> getSoCon(String issueName, String solName);
}
