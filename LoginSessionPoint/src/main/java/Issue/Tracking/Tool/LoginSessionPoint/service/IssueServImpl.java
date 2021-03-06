package Issue.Tracking.Tool.LoginSessionPoint.service;

import Issue.Tracking.Tool.LoginSessionPoint.domain.*;
import Issue.Tracking.Tool.LoginSessionPoint.repo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j

public class IssueServImpl implements  IssueService {
    private final UserRepo userRepo;
    private final UserGroupRepo userGroupRepo;
    private final IssueRepo issueRepo;
    private final SolutionRepo solutionRepo;
    private final PriorityRepo priorityRepo;
    private final StatusRepo statusRepo;


    @Override
    public Issue saveIssue(Issue issue) {
        log.info("Saving  Issue  {} to DB", issue.getName());




        List<APIUser> users = new ArrayList<>();
        try {
            for (APIUser user : issue.getUsers())
                if (userRepo.findFirstByUsername(user.getUsername()) != null) {
                    users.add(userRepo.findFirstByUsername(user.getUsername()));
                }
            issue.getUsers().clear();
        } catch (NullPointerException i) {
            issue.setUsers(null);
        }
//

        List<UserGroup> groups = new ArrayList<>();
        try {
            groups = new ArrayList<UserGroup>();
            for (UserGroup userG : issue.getUserGroups())
                if (userGroupRepo.findByName(userG.getName()) != null) {
                    groups.add(userGroupRepo.findByName(userG.getName()));
                }
            issue.getUserGroups().clear();
        } catch (NullPointerException i) {
            issue.setUserGroups(null);
        }

        List<Solution> sols = new ArrayList<>();
        try {
            sols = new ArrayList<Solution>();
            for (Solution sol : issue.getSolutions())
                if (solutionRepo.findByName(sol.getName()) != null) {
                    sols.add(solutionRepo.findByName(sol.getName()));
                }

            issue.getSolutions().clear();

        } catch (NullPointerException i) {issue.setSolutions(null);
        }
        try {
            if (priorityRepo.findByName(issue.getPriority().getName()) != null) {
                issue.setPriority(priorityRepo.findByName(issue.getPriority().getName()));

            }

            if (statusRepo.getStatusByName((issue.getStatus().getName())) != null) {
                issue.setStatus(statusRepo.getStatusByName((issue.getStatus().getName())));
            }


        } catch (NullPointerException ignored) {
        }



try {
    issue.getUsers().clear();
    issue.getUserGroups().clear();
    issue.getSolutions().clear();
}
catch (NullPointerException ignored) {
}
        issue.setUsers(users);
        issue.setUserGroups(groups);
        issue.setSolutions(sols);




        return issueRepo.save(issue);
    }

    @Override
    public Issue getIssue(String IssueName) {
        log.info("Getting  Issue  {} ",issueRepo.findByName(IssueName));
        return issueRepo.findByName(IssueName);
    }

    @Override
    public List<Issue> getIssues() {
        log.info("Getting all issues ");

        return issueRepo.findAll();

    }

    @Override
    public List<Solution> getSols(Issue issue) {
        log.info("Getting all sols. of Issue {} ",issue.getName() );
        return issueRepo.findAllSolsByName(issue.getName());
    }

    @Override
    public List<APIUser> getContributors(Issue issue) {
        log.info("Getting all contrib. to  Issue {} ",issue.getName() );
        return issueRepo.findAllUsersByName(issue.getName());
    }

    @Override
    public List<UserGroup> getGroups(Issue issue) {
        log.info("Getting all groups of Issue {} ",issue.getName() );
        return issueRepo.findAllGroupsByName(issue.getName());

    }

    @Override
    public void AddSol(Solution solution , String IssueName) {
        log.info("Adding solution to Issue {} ",IssueName );
        Issue issue = issueRepo.findByName(IssueName);
        issue.getSolutions().add(solution);
    }

    @Override
    public void AddUser(APIUser user, String IssueName) {
        log.info("Adding Contributor to Issue {} ",IssueName );
        Issue issue = issueRepo.findByName(IssueName);
        issue.getUsers().add(user);
    }

    @Override
    public void AddGroup(UserGroup userGroup, String IssueName) {
        log.info("Adding UserGroup to Issue {} ",IssueName );
        Issue issue = issueRepo.findByName(IssueName);
        issue.getUserGroups().add(userGroup);
    }

    @Override
    public void AddPriority(Priority priority, String IssueName) {
        Issue issue = issueRepo.findByName(IssueName);
        issue.setPriority(priority);
    }

    @Override
    public void AddStatus(Status status, String IssueName) {
        Issue issue = issueRepo.findByName(IssueName);
        issue.setStatus(status);
    }

    @Override
    public void deleteByName(String name) {

        issueRepo.deleteByName(name);
    }

    @Override
    public Date getTimestamp(String IssueName) {
        return issueRepo.findTimestampByName(IssueName);
    }


    @Override
    public String getDetails(Issue issue) {
       return issue.getDetails();
    }

    @Override
    public Status getStatus(Issue issue) {
        return issue.getStatus();
    }

    @Override
    public Priority getPrio(Issue issue) {
        return issue.getPriority();
    }


    public boolean matches_Regex(Date date, String regex){

        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(date.toString());

        return matcher.find();

    }

    @Override
    public List<Issue> findBy(String toSearch) {

        long intValue = 0L;
        Integer inputDate = null;
        try { intValue = Integer.parseInt(toSearch);}
        catch (NumberFormatException ignored){}

        try { inputDate = Integer.parseInt(toSearch);
             return issueRepo.findAll().stream().filter(issue ->  matches_Regex(issue.getDueDate(),"%" + toSearch + "%")).collect(Collectors.toList());
        }
        catch (NumberFormatException ignored){}


        return  issueRepo.findBy("%" + toSearch + "%",intValue,inputDate);
    }

    @Override
    public List<Issue> findByPrio(String toSearch) {
        return issueRepo.findAllByPriority_NameContains("%" + toSearch + "%");
    }

    @Override
    public List<Issue> findByStatus(String toSearch) {
        return issueRepo.findAllByStatus_NameContains("%" + toSearch + "%");
    }

    @Override
    public List<Issue> findByNameOfUsers(String name) {
        List <Issue> issues = new ArrayList<>();
        List<APIUser> users2 = new ArrayList<>();
        for(Issue issue: Objects.requireNonNull(issueRepo.findAll())){
           try {
               for (APIUser user : Objects.requireNonNull(issue.getUsers()))
                   if (user.getUsername().contains(name))
                       issues.add(issue);
           }
           catch (NullPointerException ignored){};
            try {
            for(UserGroup userGroup: Objects.requireNonNull(issue.getUserGroups()))
                users2  =  userGroup.getUsers().stream().filter(apiUser -> apiUser.getUsername().equals(name)).collect(Collectors.toList());
                     issues.add(issue);}
              catch (NullPointerException ignored){};
        }

        return issues;
    }

    @Override
    public List<APIUser> getSoCon(String issueName, String solName) {
        return issueRepo.getSoCon(issueName,solName);
    }


    }

