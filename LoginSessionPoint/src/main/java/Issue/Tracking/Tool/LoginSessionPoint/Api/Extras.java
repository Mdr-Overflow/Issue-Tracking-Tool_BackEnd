package Issue.Tracking.Tool.LoginSessionPoint.Api;


import Issue.Tracking.Tool.LoginSessionPoint.domain.Priority;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Status;
import Issue.Tracking.Tool.LoginSessionPoint.exception.NoDataFoundException;
import Issue.Tracking.Tool.LoginSessionPoint.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@RequestMapping(path = "/LoginSessionPoint")
@RequiredArgsConstructor
public class Extras {
    private final UserService userService;
    private final UserGroupService userGroupService;
    private final IssueService issueService;
    private final SolutionService solutionService;
    private final StatusService statusService;
    private final PriorityService priorityService;


    @ResponseBody
    @GetMapping("Extras/Priority/getAll")
    public ResponseEntity<List<Priority>> getPrios() {

        return ResponseEntity.ok().body(priorityService.getPrios());

    }


    @ResponseBody
    @GetMapping("Extras/Priority/get/{name}")
    public ResponseEntity<Priority> getPrios(@PathVariable String name) {

        return ResponseEntity.ok().body(priorityService.getPrio(name));

    }

    @ResponseBody
    @PostMapping("Extras/Priority/save")
    public String savePrio(@RequestBody Priority priority) {

       priorityService.SavePriority(priority);

       return "Saved";

    }





    @ResponseBody
    @PutMapping("Extras/Priority/update/{name}")
    public String updatePrios(@PathVariable String name, @RequestBody Priority priority) {


        Priority priorityOld = priorityService.getPrio(name);

        if (priorityOld != null) {


            if (priority.getName() != null) priorityOld.setName(priority.getName());


            priority.setLastUpdated(priority.getCreatedAt());


            priorityService.SavePriority(priorityOld);

            return "updated";
        } else throw new NoDataFoundException();
    }


    @ResponseBody
    @DeleteMapping("Extras/Priority/delete/{name}")
    public String DelPrios(@PathVariable String name) {

       Priority priority =  priorityService.getPrio(name);

       if (priority != null)
       {
           priorityService.deletePrio(priority.getName());

       }

       else  throw  new NoDataFoundException();

        return "Deleted";
    }


    @ResponseBody
    @GetMapping("Extras/Status/getAll")
    public ResponseEntity<List<Status>> getStatuses() {

        return ResponseEntity.ok().body(statusService.getStatuses());

    }


    @ResponseBody
    @GetMapping("Extras/Status/get/{name}")
    public ResponseEntity<Status> getStatus(@PathVariable String name) {

        return ResponseEntity.ok().body(statusService.getStatus(name));

    }


    @ResponseBody
    @PutMapping("Extras/Status/update/{name}")
    public String updateStatus(@PathVariable String name, @RequestBody Status status) {


        Status statusOld = statusService.getStatus(name);

        if (statusOld != null) {


            if (status.getName() != null) statusOld.setName(status.getName());


            status.setLastUpdated(status.getCreatedAt());


            statusService.SaveStatus(statusOld);

            return "updated";
        } else throw new NoDataFoundException();
    }


    @ResponseBody
    @DeleteMapping("Extras/Status/delete/{name}")
    public String DelStatuses(@PathVariable String name) {

        Status status =  statusService.getStatus(name);

        if (status != null)
        {
            statusService.deleteStatus(status.getName());

        }

        else  throw  new NoDataFoundException();

        return "Deleted";
    }


    @ResponseBody
    @PostMapping("Extras/Status/save")
    public String saveStatus(@RequestBody Status status) {

        statusService.SaveStatus(status);

        return "Saved";

    }




}