package Issue.Tracking.Tool.LoginSessionPoint.Api;

import Issue.Tracking.Tool.LoginSessionPoint.domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.domain.UserGroup;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Role;
import Issue.Tracking.Tool.LoginSessionPoint.service.UserGroupService;
import Issue.Tracking.Tool.LoginSessionPoint.service.IssueService;
import Issue.Tracking.Tool.LoginSessionPoint.service.SolutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
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
    private final Issue.Tracking.Tool.LoginSessionPoint.service.UserService userService;
    private final UserGroupService userGroupService;
    private final IssueService issueService;
    private final SolutionService solutionService;


    //private final SecurityService securityService;



    @ResponseBody
    @CrossOrigin(origins = "localhost:4200")
    @GetMapping("/user/{username}")
    public ResponseEntity<APIUser> getUserByName(@PathVariable("username") String username) {
        APIUser user = userService.getUser(username);
        return ResponseEntity.ok().body(user);
        // use fastAPIUser
    }



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




