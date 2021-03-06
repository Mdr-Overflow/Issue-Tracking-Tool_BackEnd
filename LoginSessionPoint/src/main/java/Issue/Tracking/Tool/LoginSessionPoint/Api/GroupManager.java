package Issue.Tracking.Tool.LoginSessionPoint.Api;

import Issue.Tracking.Tool.LoginSessionPoint.domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.domain.UserGroup;

import Issue.Tracking.Tool.LoginSessionPoint.domain.deletedConfirmation;
import Issue.Tracking.Tool.LoginSessionPoint.exception.LeaderNOTinGroupException;
import Issue.Tracking.Tool.LoginSessionPoint.exception.NoDataFoundException;
import Issue.Tracking.Tool.LoginSessionPoint.service.IssueService;
import Issue.Tracking.Tool.LoginSessionPoint.service.SolutionService;
import Issue.Tracking.Tool.LoginSessionPoint.service.UserGroupService;
import Issue.Tracking.Tool.LoginSessionPoint.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


import static org.springframework.http.ResponseEntity.created;

@Controller
//@RequestMapping(path = "/LoginSessionPoint")
@RequiredArgsConstructor
@Slf4j
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
    public ResponseEntity<deletedConfirmation> deleteGroup(@PathVariable String name) {
        deletedConfirmation del = new deletedConfirmation();
        if (userGroupService.getGroup(name) != null) {
            del = new deletedConfirmation(userGroupService.getGroup(name).getName());
            userGroupService.deleteByName(name);

        } else throw new NoDataFoundException();

        return ResponseEntity.ok().body(del);
    }



    @ResponseBody
    @PutMapping("/GroupManager/update/{name}")
    public ResponseEntity<UserGroup> replaceGroup(@RequestBody UserGroup group, @PathVariable String name) {

        UserGroup groupOld = userGroupService.getGroup(name);

        if(groupOld != null) {


            log.info("here got group");
            if (group.getName() != null) groupOld.setName(group.getName());
            if (group.getUsers() != null) {
                 groupOld.getUsers().clear();
                 group.getUsers().forEach(apiUser -> groupOld.getUsers().add(userService.getUser(apiUser.getUsername())));

            }
            if (group.getLeader() != null) {
                groupOld.setLeader(userService.getUser(group.getLeader().getUsername()));

            }

            group.setLastUpdated(group.getCreatedAt());


            userGroupService.saveGroup(groupOld);
            log.info("here is hard to get");

            return ResponseEntity.ok().body(groupOld);
        }
        else throw new NoDataFoundException();
    }



    @ResponseBody
    @GetMapping("/GroupManager/searchBy={ToSearch}")
    public List<UserGroup> FindBy(@PathVariable String ToSearch) {


        return userGroupService.findBy(ToSearch);


    }


    public boolean isIN(UserGroup userGroup){

        for( APIUser user : userGroup.getUsers() )
            if(userGroup.getLeader().getUsername().equals(user.getUsername()))
                return true;
        return false;
    }


    @ResponseBody
    @PostMapping("/GroupManager/save")
    public ResponseEntity<UserGroup> saveGroup(@RequestBody UserGroup userGroup) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/GroupManager/save").toUriString());

        if(!isIN(userGroup))
            throw new LeaderNOTinGroupException();

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
        if(userService.getUser(leader.getUsername()) != null && userGroupService.getGroup(GroupName) != null) {
            userGroup = userGroupService.getGroup(GroupName);
            userGroup.setLeader(userService.getUser(leader.getUsername()));
           // userService.getUser(leader.getUsername()).setGroup_id(userGroup.getId());
            userGroupService.saveGroup(userGroup);
        }
        else {
            throw new NoDataFoundException();
        }

        return ResponseEntity.ok().body(userGroup);

    }

    @ResponseBody
    @PutMapping("/GroupManager/AddUser/{GroupName}/{username}")
    public ResponseEntity<UserGroup> AddUser(@PathVariable String username, @PathVariable String GroupName) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/GroupManager/AddUser/{GroupName}").toUriString());

        UserGroup group = null;
        if(userService.getUser(username) != null && userGroupService.getGroup(GroupName) != null) {
            group = userGroupService.getGroup(GroupName);
            group.getUsers().add(userService.getUser(username));
            userGroupService.saveGroup(group);
        }
        else {
            throw new NoDataFoundException();
        }

        return  ResponseEntity.ok().body(group);

    }

    @ResponseBody
    @DeleteMapping("/GroupManager/DelUser/{GroupName}/{username}")
    public ResponseEntity<deletedConfirmation> DelUser(@PathVariable String username, @PathVariable String GroupName) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("GroupManager/DelUser/{GroupName}").toUriString());

        deletedConfirmation del = new deletedConfirmation();
        UserGroup group = null;
        if(userService.getUser(username) != null && userGroupService.getGroup(GroupName) != null) {
            group = userGroupService.getGroup(GroupName);
            group.getUsers().remove(userService.getUser(username));
          //  userService.getUser(username).setGroup_id(0L);
             del = new deletedConfirmation(username);
            userGroupService.saveGroup(group);
        }
        else {
            throw new NoDataFoundException();
        }

        return ResponseEntity.ok().body(del);

    }


    @ResponseBody
    @GetMapping("/GroupManager/usersNOgroup")
    public ResponseEntity<List<APIUser>> NOG() {


           List<APIUser> users = userService.getUsersNOGROUP();
           if(users == null) throw new  NoDataFoundException();

        return ResponseEntity.ok().body(users); //
    }







    @ResponseBody
    @GetMapping("/GroupManager/user/{username}")
    public ResponseEntity<List<UserGroup>> getGroupUsername(@PathVariable String username) {

        APIUser apiUser = userService.getUser(username);

        List<UserGroup> userGroups = userGroupService.getGroups().stream().filter(userGroup -> userGroup.getUsers().contains(apiUser)).collect(Collectors.toList());




            if(userGroups.isEmpty()) throw new NoDataFoundException();

        return ResponseEntity.ok().body(userGroups);  //
    }





    @ResponseBody
    @GetMapping("GroupManager/get/{groupName}")
    public ResponseEntity<UserGroup> getGroup(@PathVariable String groupName) {

        return ResponseEntity.ok().body(userGroupService.getGroup(groupName));

    }

    @ResponseBody
    @GetMapping("GroupManager/getLeader/{groupName}")
    public ResponseEntity<APIUser> getGroupLeader(@PathVariable String groupName) {

        return ResponseEntity.ok().body(userGroupService.getLeader(groupName));

    }



    @ResponseBody
    @GetMapping("GroupManager/getAllUsers/{groupName}")
    public ResponseEntity<List<APIUser>> getUsersOfGroup(@PathVariable String groupName) {

        return ResponseEntity.ok().body(userGroupService.getUsers(groupName));

    }

    @ResponseBody
    @GetMapping("GroupManager/getTime/{groupName}")
    public ResponseEntity<Date> getTimeGroup(@PathVariable String groupName) {

        return ResponseEntity.ok().body(userGroupService.getTimestamp(groupName));

    }

}
