package Issue.Tracking.Tool.LoginSessionPoint.Api;

import Issue.Tracking.Tool.LoginSessionPoint.domain.Issue;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Role;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Solution;
import Issue.Tracking.Tool.LoginSessionPoint.domain.UserGroup;
import Issue.Tracking.Tool.LoginSessionPoint.exception.NoDataFoundException;
import Issue.Tracking.Tool.LoginSessionPoint.service.IssueService;
import Issue.Tracking.Tool.LoginSessionPoint.service.SolutionService;
import Issue.Tracking.Tool.LoginSessionPoint.service.UserGroupService;
import Issue.Tracking.Tool.LoginSessionPoint.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;

import static org.springframework.http.ResponseEntity.created;

@Controller
//@RequestMapping(path = "/LoginSessionPoint")
@RequiredArgsConstructor
public class IssueDashboard {

    private final UserService userService;
    private final UserGroupService userGroupService;
    private final IssueService issueService;
    private final SolutionService solutionService;


    @ResponseBody
    @PostMapping("IssueDashboard/save")
    public ResponseEntity<Issue> saveIssue(@RequestBody Issue issue) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/IssueDashboard/save").toUriString());
        return created(uri).body(issueService.saveIssue(issue));

    }

    @ResponseBody
    @PostMapping("IssueDashboard/solution/save")
    public ResponseEntity<String> addSolution(@RequestBody Solution solution, String issueName) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/IssueDashboard/solution/save").toUriString());
        Issue issue = issueService.getIssue(issueName);
        issue.getSolutions().add(solution);
        return created(uri).body("Nice");
    }

    @ResponseBody
    @PutMapping("/IssueDashboard/update/{name}")
    public String replaceIssue(@RequestBody Issue issue, @PathVariable String name) {

       Issue issueOld = issueService.getIssue(name);

        if(issueOld != null) {


            if (issue.getName() != null) issueOld.setName(issue.getName());
            if (issue.getSolutions()  != null) issueOld.setSolutions(issue.getSolutions());
            if (issue.getUsers() != null) issueOld.setUsers(issue.getUsers());
            if (issue.getUserGroups() != null) issueOld.setUserGroups(issue.getUserGroups());
            if (issue.getDetails() != null) issueOld.setDetails(issue.getDetails());
            if (issue.getStatus() != null) issueOld.setStatus(issue.getStatus());
            if (issue.getPriority() != null) issueOld.setPriority(issue.getPriority());


            issue.setLastUpdated(issue.getCreatedAt());
            return "updated";
        }
        else throw new NoDataFoundException();
    }


    @ResponseBody
    @PostMapping("IssueDashboard/get")
    public ResponseEntity<Issue> getIssue(@RequestBody String IssueName) {

        return ResponseEntity.ok().body(issueService.getIssue(IssueName));

    }


    @ResponseBody
    @GetMapping("IssueDashboard/getAll")
    public ResponseEntity<List<Issue>> getAllIssues() {

        return ResponseEntity.ok().body(issueService.getIssues());

    }


    @DeleteMapping("/IssueDashboard/delete/{name}")
    public void deleteIssue(@PathVariable String name) {
        if(issueService.getIssue(name) != null)
            issueService.deleteByName(name);
        else throw new NoDataFoundException();
    }


    @ResponseBody
    @PostMapping("IssueDashboard/getTime")
    public ResponseEntity<Date> getTimeIssue(@RequestBody String IssueName) {

        return ResponseEntity.ok().body(issueService.getTimestamp(IssueName));

    }



}
