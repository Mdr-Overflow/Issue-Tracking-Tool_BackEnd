package Issue.Tracking.Tool.LoginSessionPoint.Api;

import Issue.Tracking.Tool.LoginSessionPoint.domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Issue;
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
public class GroupManager {

    private final UserService userService;
    private final UserGroupService userGroupService;
    private final IssueService issueService;
    private final SolutionService solutionService;

    @ResponseBody
    @GetMapping("GroupManager")
    public ResponseEntity<List<UserGroup>> getALLGroups() {

        return ResponseEntity.ok().body(userGroupService.getGroups());

    }

    @DeleteMapping("/GroupManager/delete/{name}")
    public void deleteGroup(@PathVariable String name) {
        if(userGroupService.getGroup(name) != null)
        userGroupService.deleteByName(name);
        else throw new NoDataFoundException();
    }



    @ResponseBody
    @PutMapping("/GroupManager/update/{name}")
    public String replaceGroup(@RequestBody UserGroup group, @PathVariable String name) {

        UserGroup groupOld = userGroupService.getGroup(name);
        if(groupOld != null) {


            if (group.getName() != null) groupOld.setName(group.getName());
            if (group.getUsers() != null) groupOld.setUsers(group.getUsers());
            if (group.getUsers() != null) groupOld.setLeader(group.getLeader());


            group.setLastUpdated(group.getCreatedAt());


            userGroupService.saveGroup(groupOld);


            return "updated";
        }
        else throw new NoDataFoundException();
    }



    @ResponseBody
    @PostMapping("/GroupManager/save")
    public ResponseEntity<UserGroup> saveGroup(@RequestBody UserGroup userGroup) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/GroupManager/save").toUriString());
        if(userService.getUser(userGroup.getLeader().getUsername()) == null)
            throw new NoDataFoundException();
        for(APIUser user : userGroup.getUsers())
        {
            if (userService.getUser(user.getUsername()) == null)
                throw new NoDataFoundException();
        }

        return created(uri).body(userGroupService.saveGroup(userGroup));

    }


    @ResponseBody
    @PutMapping("/GroupManager/changeLeader/{GroupName}")
    public String saveLeader(@RequestBody APIUser leader, @PathVariable String GroupName) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/GroupManager/changeLeader").toUriString());

        if(userService.getUser(leader.getUsername()) != null)
        userGroupService.getGroup(GroupName).setLeader(leader);
        else {
            throw new NoDataFoundException();
        }

        return "Updated";

    }

    @ResponseBody
    @PutMapping("/GroupManager/AddUser/{GroupName}")
    public String AddUser(@RequestBody APIUser user, @PathVariable String GroupName) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/GroupManager/AddUser/{GroupName}").toUriString());

        if(userService.getUser(user.getUsername()) != null) {
            UserGroup group = userGroupService.getGroup(GroupName);
            group.getUsers().add(user);
            userGroupService.saveGroup(group);
        }
        else {
            throw new NoDataFoundException();
        }

        return "Updated";

    }

    @ResponseBody
    @DeleteMapping("/GroupManager/DelUser/{GroupName}")
    public String DelUser(@RequestBody APIUser user, @PathVariable String GroupName) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("GroupManager/DelUser/{GroupName}").toUriString());

        if(userService.getUser(user.getUsername()) != null) {
            UserGroup group = userGroupService.getGroup(GroupName);
            group.getUsers().remove(user);
            userGroupService.saveGroup(group);
        }
        else {
            throw new NoDataFoundException();
        }

        return "Updated";

    }





    @ResponseBody
    @PostMapping("GroupManager/addUser")
    public ResponseEntity<String> saveGroup(@RequestBody String Username, String GroupName) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/GroupManager/addUser").toUriString());
        userGroupService.AddUserToGroup(Username, GroupName);
        return created(uri).body("Nice");
    }


    @ResponseBody
    @GetMapping("GroupManager/get")
    public ResponseEntity<UserGroup> getGroup(@RequestBody String groupName) {

        return ResponseEntity.ok().body(userGroupService.getGroup(groupName));

    }

    @ResponseBody
    @GetMapping("GroupManager/getLeader")
    public ResponseEntity<APIUser> getGroupLeader(@RequestBody String groupName) {

        return ResponseEntity.ok().body(userGroupService.getLeader(groupName));

    }



    @ResponseBody
    @GetMapping("GroupManager/getAllUsers")
    public ResponseEntity<List<APIUser>> getUsersOfGroup(@RequestBody String groupName) {

        return ResponseEntity.ok().body(userGroupService.getUsers(groupName));

    }

    @ResponseBody
    @GetMapping("GroupManager/getTime")
    public ResponseEntity<Date> getTimeGroup(@RequestBody String groupName) {

        return ResponseEntity.ok().body(userGroupService.getTimestamp(groupName));

    }

}
