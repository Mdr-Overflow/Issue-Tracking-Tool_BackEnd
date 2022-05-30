package Issue.Tracking.Tool.LoginSessionPoint.service;

import Issue.Tracking.Tool.LoginSessionPoint.domain.*;
import Issue.Tracking.Tool.LoginSessionPoint.repo.SolutionRepo;
import Issue.Tracking.Tool.LoginSessionPoint.repo.UserGroupRepo;
import Issue.Tracking.Tool.LoginSessionPoint.repo.IssueRepo;
import Issue.Tracking.Tool.LoginSessionPoint.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j

public class IssueServImpl implements  IssueService {
    private final UserRepo userRepo;
    private final UserGroupRepo userGroupRepo;
    private final IssueRepo issueRepo;
    private final SolutionRepo solutionRepo;
    //private final Priority userRepo;
    //private final Status   userRepo;

    @Override
    public Issue saveIssue(Issue issue) {
        log.info("Saving  Issue  {} to DB",issue.getName());

        List<APIUser> users = new ArrayList<APIUser>();
        for (APIUser user: issue.getUsers())
            if(userRepo.findFirstByUsername(user.getUsername()) != null){
                users.add(userRepo.findFirstByUsername(user.getUsername()));
            }


        List<UserGroup> groups = new ArrayList<UserGroup>();
        for (UserGroup userG: issue.getUserGroups())
            if(userGroupRepo.findByName(userG.getName()) != null){
                groups.add(userGroupRepo.findByName(userG.getName()));
            }

        List<Solution> sols = new ArrayList<Solution>();
        for (Solution sol: issue.getSolutions())
            if(solutionRepo.findByName(sol.getName()) != null){
                sols.add(solutionRepo.findByName(sol.getName()));
            }


       // log.info(users.toString());
        issue.getUsers().clear();
        issue.getUserGroups().clear();
        issue.getSolutions().clear();

        issue.setUsers(users);
        issue.setUserGroups(groups);
        issue.setSolutions(sols);

       // log.info(userGroup.getUsers().toString());



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
        //org.springframework.data.domain.Pageable givenPage =  PageRequest.of(0, 100, Sort.unsorted()); // Page has overhead cost (determines how many beforehand)
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

    @Override
    public List<Issue> findBy(String toSearch) {

        long intValue = 0L;
        try { intValue = Integer.parseInt(toSearch);}
        catch (NumberFormatException ignored){}


        return  issueRepo.findBy("%" + toSearch + "%",intValue);
    }

    @Override
    public List<Issue> findByPrio(String toSearch) {
        return issueRepo.findAllByPriority_NameContains("%" + toSearch + "%");
    }

    @Override
    public List<Issue> findByStatus(String toSearch) {
        return issueRepo.findAllByStatus_NameContains("%" + toSearch + "%");
    }
}
