package Issue.Tracking.Tool.LoginSessionPoint.Api;

import Issue.Tracking.Tool.LoginSessionPoint.domain.*;
import Issue.Tracking.Tool.LoginSessionPoint.exception.NoDataFoundException;
import Issue.Tracking.Tool.LoginSessionPoint.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class IssueDashboard {

    private final UserService userService;
    private final UserGroupService userGroupService;
    private final IssueService issueService;
    private final SolutionService solutionService;
    private final StatusService statusService;
    private final PriorityService priorityService;

    @ResponseBody
    @PostMapping("IssueDashboard/save")
    public ResponseEntity<Issue> saveIssue(@RequestBody Issue issue) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/IssueDashboard/save").toUriString());
        return created(uri).body(issueService.saveIssue(issue));

    }

    @ResponseBody
    @PostMapping("IssueDashboard/solution/save/{issueName}")
    public ResponseEntity<Solution> addSolution(@RequestBody Solution solution, @PathVariable String issueName) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/IssueDashboard/solution/save/{issueName}").toUriString());
        Issue issue = issueService.getIssue(issueName);
        issue.getSolutions().add(solution);
        issueService.saveIssue(issue);
        return created(uri).body(solution);
    }

    @ResponseBody
    @PutMapping("IssueDashboard/solution/add/{issueName}")
    public ResponseEntity<Solution> addSolution2(@RequestBody Solution solution, @PathVariable String issueName) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/IssueDashboard/solution/add/{issueName}").toUriString());
        Issue issue = issueService.getIssue(issueName);

        Solution sol = solutionService.getSolution(issueName);
        if(sol == null)
            throw new NoDataFoundException();

        issue.getSolutions().add(solution);
        issueService.saveIssue(issue);
        return created(uri).body(solution);
    }



    @ResponseBody
    @DeleteMapping("IssueDashboard/solution/remove/{issueName}")  //sol persists
    public ResponseEntity<Issue> remSolution(@RequestBody Solution solution, @PathVariable String issueName) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/IssueDashboard/solution/remove/{issueDelete}").toUriString());
        Issue issue = issueService.getIssue(issueName);


        Solution sol =  solutionService.getSolution(solution.getName());
        if(sol == null) throw new NoDataFoundException();


        issue.getSolutions().remove(sol);
        issueService.saveIssue(issue);
        return created(uri).body(issue);
    }


    @ResponseBody
    @DeleteMapping("IssueDashboard/solution/delete/{issueName}")  //sol deletes
    public ResponseEntity<Issue> delSolution(@RequestBody Solution solution, @PathVariable String issueName) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/IssueDashboard/solution/delete/{issueDelete}").toUriString());

        Issue issue = issueService.getIssue(issueName);
        Solution sol =  solutionService.getSolution(solution.getName());
        if(sol == null) throw new NoDataFoundException();

        issue.getSolutions().remove(sol);
        solutionService.deleteSol(sol);
        issueService.saveIssue(issue);

        return created(uri).body(issue);
    }





    @ResponseBody
    @PutMapping("/IssueDashboard/admin/update/{name}")
    public ResponseEntity<Issue> replaceIssue(@RequestBody Issue issue, @PathVariable String name) {

       Issue issueOld = issueService.getIssue(name);

        if(issueOld != null) {

            if (issue.getName() != null) issueOld.setName(issue.getName());


            if (issue.getUsers() != null) {
                issueOld.getUsers().clear();
                issue.getUsers().forEach(apiUser -> issueOld.getUsers().add(userService.getUser(apiUser.getUsername())));
            }
            if (issue.getDetails() != null) issueOld.setDetails(issue.getDetails());

            if (issue.getStatus() != null) issueOld.setStatus(statusService.getStatus(issue.getStatus().getName()));
            if (issue.getPriority() != null) issueOld.setPriority(priorityService.getPrio(issue.getPriority().getName()));

            if (issue.getSolutions()  != null) {
                issueOld.getSolutions().clear();
                issue.getSolutions().forEach(solution -> issueOld.getSolutions().add(solutionService.getSolution(solution.getName())));
            }

            if (issue.getUserGroups() != null)
            {
                issueOld.getUserGroups().clear();
                issue.getUserGroups().forEach(userGroup -> issueOld.getUserGroups().add(userGroupService.getGroup(userGroup.getName())));
            }



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


            if (issue.getUsers() != null) {
                issueOld.getUsers().clear();
                issue.getUsers().forEach(apiUser -> issueOld.getUsers().add(userService.getUser(apiUser.getUsername())));
            }
            if (issue.getDetails() != null) issueOld.setDetails(issue.getDetails());

            if (issue.getStatus() != null) issueOld.setStatus(statusService.getStatus(issue.getStatus().getName()));
            if (issue.getPriority() != null) issueOld.setPriority(priorityService.getPrio(issue.getPriority().getName()));

            if (issue.getSolutions()  != null) {
                issueOld.getSolutions().clear();
                issue.getSolutions().forEach(solution -> issueOld.getSolutions().add(solutionService.getSolution(solution.getName())));
            }


            issue.setLastUpdated(issue.getCreatedAt());


            issueService.saveIssue(issueOld);

            return ResponseEntity.ok().body(issueOld);
        }
        else throw new NoDataFoundException();
    }



    @DeleteMapping("/IssueDashboard/delete/{name}")
    public ResponseEntity<deletedConfirmation> deleteIssue(@PathVariable String name) {
        deletedConfirmation del;
        if (issueService.getIssue(name) != null) {
            issueService.deleteByName(name);
            del = new deletedConfirmation(name);
        } else throw new NoDataFoundException();

        return ResponseEntity.ok().body(del);
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


        return issueService.findBy(ToSearch);


    }


    @ResponseBody
    @GetMapping("/IssueDashboard/Solution/searchBy={ToSearch}")
    public List<Solution> SOLFindBy(@PathVariable String ToSearch) {

        List<Solution> solutions = solutionService.findBy(ToSearch);

        log.info(solutions.toString() + " BY " + ToSearch);



        return solutions;


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
