package Issue.Tracking.Tool.LoginSessionPoint.service;

import Issue.Tracking.Tool.LoginSessionPoint.domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.domain.UserGroup;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Issue;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Solution;
import Issue.Tracking.Tool.LoginSessionPoint.repo.UserGroupRepo;
import Issue.Tracking.Tool.LoginSessionPoint.repo.IssueRepo;
import Issue.Tracking.Tool.LoginSessionPoint.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    //private final Priority userRepo;
    //private final Status   userRepo;

    @Override
    public Issue saveIssue(Issue issue) {
        log.info("Saving  Issue  {} to DB",issue.getName());
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
        org.springframework.data.domain.Pageable givenPage =  PageRequest.of(0, 5, Sort.unsorted()); // Page has overhead cost (determines how many beforehand)
        return (issueRepo.findAll(givenPage)).getContent();

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
    public void deleteByName(String name) {
        issueRepo.deleteByName(name);
    }

    @Override
    public Date getTimestamp(String IssueName) {
        return issueRepo.findTimestampByName(IssueName);
    }
}
