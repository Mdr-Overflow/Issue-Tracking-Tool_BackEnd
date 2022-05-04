package Issue.Tracking.Tool.LoginSessionPoint.Api;

import Issue.Tracking.Tool.LoginSessionPoint.Domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.Domain.UserGroup;
import Issue.Tracking.Tool.LoginSessionPoint.Domain.Role;
import Issue.Tracking.Tool.LoginSessionPoint.Service.UserGroupService;
import Issue.Tracking.Tool.LoginSessionPoint.Service.IssueService;
import Issue.Tracking.Tool.LoginSessionPoint.Service.SolutionService;
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
public class UserServiceClass {
    private final Issue.Tracking.Tool.LoginSessionPoint.Service.UserService userService;
    private final UserGroupService userGroupService;
    private final IssueService issueService;
    private final SolutionService solutionService;


    //private final SecurityService securityService;

    @ResponseBody
    @GetMapping("/user")
    public ResponseEntity<List<APIUser>> getUsers() {

        return ResponseEntity.ok().body(userService.getUsers());

    }


    //testing only
    @ResponseBody
    @GetMapping("role")
    public ResponseEntity<List<Role>> getALLRoles() {

        return ResponseEntity.ok().body(userService.getALLRoles());

    }



    @ResponseBody
    @GetMapping("GroupManager")
    public ResponseEntity<List<UserGroup>> getALLGroups() {

        return ResponseEntity.ok().body(userGroupService.getGroups());

    }


    @ResponseBody
    @PostMapping("GroupManager/save")
    public ResponseEntity<UserGroup> saveGroup(@RequestBody UserGroup userGroup) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/GroupManager/save").toUriString());
        return created(uri).body(userGroupService.saveGroup(userGroup));

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
    @GetMapping("GroupManager/getAll")
    public ResponseEntity<List<UserGroup>> saveGroup() {

        return ResponseEntity.ok().body(userGroupService.getGroups());

    }

    @ResponseBody
    @GetMapping("GroupManager/getTime")
    public ResponseEntity<Date> getTimeGroup(@RequestBody String groupName) {

        return ResponseEntity.ok().body(userGroupService.getTimestamp(groupName));

    }











    //testing only

    // @GetMapping


    //testing only
    @ResponseBody
    @PostMapping("user/save")
    public ResponseEntity<APIUser> saveUser(@RequestBody APIUser user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/LoginSessionPoint/user/save").toUriString());
        return created(uri).body(userService.saveUser(user));
    }

    @ResponseBody
    @PostMapping("role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/LoginSessionPoint/role/save").toUriString());
        return created(uri).body(userService.saveRole(role));
    }

    @ResponseBody
    @PostMapping("role/addtouser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

}




