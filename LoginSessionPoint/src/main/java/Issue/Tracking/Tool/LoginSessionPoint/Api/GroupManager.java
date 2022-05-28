package Issue.Tracking.Tool.LoginSessionPoint.Api;

import Issue.Tracking.Tool.LoginSessionPoint.domain.APIUser;
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
    public ResponseEntity<UserGroup> deleteGroup(@PathVariable String name) {
        if(userGroupService.getGroup(name) != null)
        userGroupService.deleteByName(name);
        else throw new NoDataFoundException();

        return ResponseEntity.ok().body(null);
    }



    @ResponseBody
    @PutMapping("/GroupManager/update/{name}")
    public ResponseEntity<UserGroup> replaceGroup(@RequestBody UserGroup group, @PathVariable String name) {

        UserGroup groupOld = userGroupService.getGroup(name);
        if(groupOld != null) {


            if (group.getName() != null) groupOld.setName(group.getName());
            if (group.getUsers() != null) groupOld.setUsers(group.getUsers());
            if (group.getUsers() != null) groupOld.setLeader(group.getLeader());


            group.setLastUpdated(group.getCreatedAt());


            userGroupService.saveGroup(groupOld);


            return ResponseEntity.ok().body(groupOld);
        }
        else throw new NoDataFoundException();
    }



    @ResponseBody
    @GetMapping("/GroupManager/searchBy={ToSearch}")
    public List<UserGroup> FindBy(@PathVariable String ToSearch) {


        return userGroupService.findBy(ToSearch);


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

       // userGroup.setLeader(get);
        return created(uri).body(userGroupService.saveGroup(userGroup));

    }


    @ResponseBody
    @PutMapping("/GroupManager/changeLeader/{GroupName}")
    public ResponseEntity<UserGroup> saveLeader(@RequestBody APIUser leader, @PathVariable String GroupName) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/GroupManager/changeLeader").toUriString());

        UserGroup userGroup = null;
        if(userService.getUser(leader.getUsername()) != null) {
            userGroup = userGroupService.getGroup(GroupName);
            userGroup.setLeader(leader);
            userGroupService.saveGroup(userGroup);
        }
        else {
            throw new NoDataFoundException();
        }

        return ResponseEntity.ok().body(userGroup);

    }

    @ResponseBody
    @PutMapping("/GroupManager/AddUser/{GroupName}")
    public ResponseEntity<UserGroup> AddUser(@RequestBody APIUser user, @PathVariable String GroupName) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/GroupManager/AddUser/{GroupName}").toUriString());

        UserGroup group = null;
        if(userService.getUser(user.getUsername()) != null) {
            group = userGroupService.getGroup(GroupName);
            group.getUsers().add(user);
            userGroupService.saveGroup(group);
        }
        else {
            throw new NoDataFoundException();
        }

        return  ResponseEntity.ok().body(group);

    }

    @ResponseBody
    @DeleteMapping("/GroupManager/DelUser/{GroupName}")
    public ResponseEntity<UserGroup> DelUser(@RequestBody APIUser user, @PathVariable String GroupName) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("GroupManager/DelUser/{GroupName}").toUriString());

        UserGroup group = null;
        if(userService.getUser(user.getUsername()) != null) {
            group = userGroupService.getGroup(GroupName);
            group.getUsers().remove(user);
            userGroupService.saveGroup(group);
        }
        else {
            throw new NoDataFoundException();
        }

        return ResponseEntity.ok().body(group);

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
