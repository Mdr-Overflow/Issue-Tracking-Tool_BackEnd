package Issue.Tracking.Tool.LoginSessionPoint.Api;

import Issue.Tracking.Tool.LoginSessionPoint.domain.*;
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
    @PutMapping("/IssueDashboard/admin/update/{name}")
    public ResponseEntity<Issue> replaceIssue(@RequestBody Issue issue, @PathVariable String name) {

       Issue issueOld = issueService.getIssue(name);

        if(issueOld != null) {


            if (issue.getName() != null) issueOld.setName(issue.getName());

            if (issue.getSolutions()  != null)
            if (issue.getUsers() != null) issueOld.setUsers(issue.getUsers());
            if (issue.getUserGroups() != null) issueOld.setUserGroups(issue.getUserGroups());

            if (issue.getDetails() != null) issueOld.setDetails(issue.getDetails());

            if (issue.getStatus() != null)  issueOld.setStatus(issue.getStatus());
            if (issue.getPriority() != null)  issueOld.setPriority(issue.getPriority());


            issue.setLastUpdated(issue.getCreatedAt());


            issueService.saveIssue(issueOld);

            return ResponseEntity.ok().body(issueOld);
        }
        else throw new NoDataFoundException();
    }

    @ResponseBody
    @PutMapping("/IssueDashboard/user/update/{name}")
    public ResponseEntity<Issue> replaceIssueUSER(@RequestBody Issue issue, @PathVariable String name) {

        Issue issueOld = issueService.getIssue(name);

        if(issueOld != null) {


            if (issue.getName() != null) issueOld.setName(issue.getName());
            if (issue.getDetails() != null) issueOld.setDetails(issue.getDetails());

            issue.setLastUpdated(issue.getCreatedAt());


            issueService.saveIssue(issueOld);

            return ResponseEntity.ok().body(issueOld);
        }
        else throw new NoDataFoundException();
    }


    @ResponseBody
    @PutMapping("/IssueDashboard/leader/update/{name}")
    public ResponseEntity<Issue> replaceIssueLEADER(@RequestBody Issue issue, @PathVariable String name) {

        Issue issueOld = issueService.getIssue(name);

        if(issueOld != null) {


            if (issue.getName() != null) issueOld.setName(issue.getName());


            if (issue.getUsers() != null) issueOld.setUsers(issue.getUsers());
            if (issue.getDetails() != null) issueOld.setDetails(issue.getDetails());
            if (issue.getStatus() != null) issueOld.setStatus(issue.getStatus());
            if (issue.getPriority() != null) issueOld.setPriority(issue.getPriority());
            if (issue.getSolutions()  != null) issueOld.setSolutions(issue.getSolutions());



            issue.setLastUpdated(issue.getCreatedAt());


            issueService.saveIssue(issueOld);

            return ResponseEntity.ok().body(issueOld);
        }
        else throw new NoDataFoundException();
    }



    @DeleteMapping("/IssueDashboard/delete/{name}")
    public void deleteIssue(@PathVariable String name) {
        if(issueService.getIssue(name) != null)
            issueService.deleteByName(name);
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

    @ResponseBody
    @GetMapping("IssueDashboard/getSols")
    public ResponseEntity<List<Solution>> getSols(@RequestBody Issue issue) {

        return ResponseEntity.ok().body(issueService.getSols(issue));

    }


    @ResponseBody
    @GetMapping("IssueDashboard/getSol/name={name_}||owner={ownerName}||description={description_}||content={content_}||type={type_}")
    public ResponseEntity<List<Solution>> getSol(@PathVariable String name_ , @PathVariable String ownerName, @PathVariable String description_,
                                           @PathVariable String content_, @PathVariable String type_) {

        // one atribute to search for or many ?????

        //many -> add all to a list then find most common 3 -> send most common 3

        //one // if path empty -> @ instead
        if(solutionService.getSolution(name_)!= null ) {
            return  ResponseEntity.ok(List.of(solutionService.getSolution(name_)));

        }
        if(solutionService.getSolutionByOwner(ownerName)!= null ) {
            return  ResponseEntity.ok(solutionService.getSolutionByOwner(ownerName));
        }
        if(solutionService.getSolutionByDescription(description_)!= null ) {
            return  ResponseEntity.ok(solutionService.getSolutionByDescription(description_));
        }

        if(solutionService.getSolutionByContent(content_)!= null ) {
            return  ResponseEntity.ok(solutionService.getSolutionByContent(content_));
        }

        if(solutionService.getSolutionByType(type_)!= null ) {
            return  ResponseEntity.ok(solutionService.getSolutionByType(type_));
        }

      //  return ResponseEntity.ok(

        throw new NoDataFoundException();


    }



    @ResponseBody
    @GetMapping("/IssueDashboard/searchBy={ToSearch}")
    public List<Issue> FindBy(@PathVariable String ToSearch) {

        List<Issue>issues = issueService.findBy(ToSearch);

        List<Issue> issuesPrio = issueService.findByPrio(ToSearch);

        List<Issue> issuesStatus = issueService.findByStatus(ToSearch);



        if(issuesPrio != null) {
            issues.addAll(issuesPrio);
        }

        if(issuesStatus != null) {
            issues.addAll(issuesStatus);
        }

        return issues;


    }


    @ResponseBody
    @GetMapping("IssueDashboard/getContrib")
    public ResponseEntity<List<APIUser>> getColabs(@RequestBody Issue issue) {

        return ResponseEntity.ok().body(issueService.getContributors(issue));

    }

    @ResponseBody
    @GetMapping("IssueDashboard/getGroups")
    public ResponseEntity<List<UserGroup>> getGroups(@RequestBody Issue issue) {

        return ResponseEntity.ok().body(issueService.getGroups(issue));

    }


    @ResponseBody
    @GetMapping("IssueDashboard/getPriority")
    public ResponseEntity<Priority> getPrio(@RequestBody Issue issue) {

        return ResponseEntity.ok().body(issueService.getPrio(issue));

    }

    @ResponseBody
    @GetMapping("IssueDashboard/getStatus")
    public ResponseEntity<Status> getStatus(@RequestBody Issue issue) {

        return ResponseEntity.ok().body(issueService.getStatus(issue));

    }


    @ResponseBody
    @GetMapping("IssueDashboard/getDetails")
    public ResponseEntity<String> getDetails(@RequestBody Issue issue) {

        return ResponseEntity.ok().body(issueService.getDetails(issue));

    }



    @ResponseBody
    @PostMapping("IssueDashboard/getTime")
    public ResponseEntity<Date> getTimeIssue(@RequestBody String IssueName) {

        return ResponseEntity.ok().body(issueService.getTimestamp(IssueName));

    }



}
