package Issue.Tracking.Tool.LoginSessionPoint.Api;

import Issue.Tracking.Tool.LoginSessionPoint.domain.Issue;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Solution;
import Issue.Tracking.Tool.LoginSessionPoint.domain.UserGroup;
import Issue.Tracking.Tool.LoginSessionPoint.service.IssueService;
import Issue.Tracking.Tool.LoginSessionPoint.service.SolutionService;
import Issue.Tracking.Tool.LoginSessionPoint.service.UserGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;

import static org.springframework.http.ResponseEntity.created;

@Controller
//@RequestMapping(path = "/LoginSessionPoint")
@RequiredArgsConstructor
public class IssueDashboard {

    private final Issue.Tracking.Tool.LoginSessionPoint.service.UserService userService;
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
    @GetMapping("IssueDashboard/get")
    public ResponseEntity<Issue> getIssue(@RequestBody String IssueName) {

        return ResponseEntity.ok().body(issueService.getIssue(IssueName));

    }


    @ResponseBody
    @GetMapping("IssueDashboard/getAll")
    public ResponseEntity<List<Issue>> getAllIssues() {

        return ResponseEntity.ok().body(issueService.getIssues());

    }

    @ResponseBody
    @GetMapping("IssueDashboard/getTime")
    public ResponseEntity<Date> getTimeIssue(@RequestBody String IssueName) {

        return ResponseEntity.ok().body(issueService.getTimestamp(IssueName));

    }



}
