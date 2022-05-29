package Issue.Tracking.Tool.LoginSessionPoint.Api;


import Issue.Tracking.Tool.LoginSessionPoint.domain.Priority;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Status;
import Issue.Tracking.Tool.LoginSessionPoint.domain.deletedConfirmation;
import Issue.Tracking.Tool.LoginSessionPoint.exception.NoDataFoundException;
import Issue.Tracking.Tool.LoginSessionPoint.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@RequestMapping(path = "/LoginSessionPoint")
@RequiredArgsConstructor
@Slf4j
public class Extras {
    private final UserService userService;
    private final UserGroupService userGroupService;
    private final IssueService issueService;
    private final SolutionService solutionService;
    private final StatusService statusService;
    private final PriorityService priorityService;


    @ResponseBody
    @GetMapping("/Extras/Priority/getAll")
    public ResponseEntity<List<Priority>> getPrios() {

        return ResponseEntity.ok().body(priorityService.getPrios());

    }


    @ResponseBody
    @GetMapping("/Extras/Priority/get/{name}")
    public ResponseEntity<Priority> getPrios(@PathVariable String name) {

        return ResponseEntity.ok().body(priorityService.getPrio(name));

    }

    @ResponseBody
    @PostMapping("/Extras/Priority/save")
    public ResponseEntity<Priority> savePrio(@RequestBody Priority priority) {

       priorityService.SavePriority(priority);

       return ResponseEntity.ok().body(priority);

    }





    @ResponseBody
    @PutMapping("/Extras/Priority/update/{name}")
    public ResponseEntity<Priority> updatePrios(@PathVariable String name, @RequestBody Priority priority) {


        Priority priorityOld = priorityService.getPrio(name);

        if (priorityOld != null) {


            if (priority.getName() != null) priorityOld.setName(priority.getName());


            priority.setLastUpdated(priority.getCreatedAt());


            priorityService.SavePriority(priorityOld);

            return ResponseEntity.ok().body(priorityOld);

        } else throw new NoDataFoundException();
    }


    @ResponseBody
    @DeleteMapping("/Extras/Priority/delete/{name}")
    public ResponseEntity<deletedConfirmation> DelPrios(@PathVariable String name) {

       Priority priority =  priorityService.getPrio(name);
        deletedConfirmation del = new deletedConfirmation();
       if (priority != null)
       {
           priorityService.deletePrio(priority.getName());
           del = new deletedConfirmation(name);
       }

       else  throw  new NoDataFoundException();

        return ResponseEntity.ok().body(del);
    }

    @ResponseBody
    @GetMapping("/Extras/Priority/searchBy={ToSearch}")
    public List<Priority> PFindBy(@PathVariable String ToSearch) {

        List<Priority> priorities = priorityService.findBy(ToSearch);

        log.info(priorities.toString() + " BY " + ToSearch);



        return priorities;


    }







    @ResponseBody
    @GetMapping("/Extras/Status/getAll")
    public ResponseEntity<List<Status>> getStatuses() {

        return ResponseEntity.ok().body(statusService.getStatuses());

    }


    @ResponseBody
    @GetMapping("/Extras/Status/get/{name}")
    public ResponseEntity<Status> getStatus(@PathVariable String name) {

        return ResponseEntity.ok().body(statusService.getStatus(name));

    }


    @ResponseBody
    @PutMapping("/Extras/Status/update/{name}")
    public ResponseEntity<Status> updateStatus(@PathVariable String name, @RequestBody Status status) {


        Status statusOld = statusService.getStatus(name);

        if (statusOld != null) {


            if (status.getName() != null) statusOld.setName(status.getName());


            status.setLastUpdated(status.getCreatedAt());


            statusService.SaveStatus(statusOld);

            return ResponseEntity.ok().body(statusOld);
        } else throw new NoDataFoundException();
    }


    @ResponseBody
    @DeleteMapping("/Extras/Status/delete/{name}")
    public ResponseEntity<deletedConfirmation> DelStatuses(@PathVariable String name) {

        Status status =  statusService.getStatus(name);
        deletedConfirmation del = new deletedConfirmation();
        if (status != null)
        {
            statusService.deleteStatus(status.getName());
            del = new deletedConfirmation(name);
        }

        else  throw  new NoDataFoundException();

        return ResponseEntity.ok().body(del);
    }


    @ResponseBody
    @PostMapping("/Extras/Status/save")
    public ResponseEntity<Status> saveStatus(@RequestBody Status status) {

        statusService.SaveStatus(status);

        return ResponseEntity.ok().body(status);

    }

    @ResponseBody
    @GetMapping("/Extras/Status/searchBy={ToSearch}")
    public List<Status> RFindBy(@PathVariable String ToSearch) {

        List<Status> statuses= statusService.findBy(ToSearch);

        log.info(statuses.toString() + " BY " + ToSearch);



        return statuses;


    }





}