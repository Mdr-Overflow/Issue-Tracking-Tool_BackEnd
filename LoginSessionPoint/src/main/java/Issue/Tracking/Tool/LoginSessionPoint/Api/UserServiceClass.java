package Issue.Tracking.Tool.LoginSessionPoint.Api;

import Issue.Tracking.Tool.LoginSessionPoint.Domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.Domain.Group;
import Issue.Tracking.Tool.LoginSessionPoint.Domain.Role;
import Issue.Tracking.Tool.LoginSessionPoint.Service.GroupService;
import Issue.Tracking.Tool.LoginSessionPoint.Service.IssueService;
import Issue.Tracking.Tool.LoginSessionPoint.Service.SecurityService;
import Issue.Tracking.Tool.LoginSessionPoint.Service.SolutionService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.GeneratedReferenceTypeDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.ResponseEntity.created;

@Controller
//@RequestMapping(path = "/LoginSessionPoint")
@RequiredArgsConstructor
public class UserServiceClass {
    private final Issue.Tracking.Tool.LoginSessionPoint.Service.UserService userService;
    private final GroupService groupService;
    private final IssueService issueService;
    private final SolutionService solutionService;


    private final SecurityService securityService;

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

    @GetMapping("/LoginSession")

    public String LoginSession() {
        if (securityService.isAuthenticated()) {
            return "redirect:/user";
        } else {

            return "LoginSession";
        }

    }


    @ResponseBody
    @GetMapping("GroupManager")
    public ResponseEntity<List<Group>> getALLGroups() {

        return ResponseEntity.ok().body(groupService.getGroups());

    }


    @ResponseBody
    @PostMapping("GroupManager/save")
    public ResponseEntity<Group> saveGroup(@RequestBody Group group) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/GroupManager/save").toUriString());
        return created(uri).body(groupService.saveGroup(group));

    }

    @ResponseBody
    @PostMapping("GroupManager/addUser")
    public ResponseEntity<String> saveGroup(@RequestBody String Username, String GroupName) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/GroupManager/addUser").toUriString());
        groupService.AddUserToGroup(Username, GroupName);
        return created(uri).body("Nice");
    }


    @ResponseBody
    @GetMapping("GroupManager/get")
    public ResponseEntity<Group> getGroup(@RequestBody String groupName) {

        return ResponseEntity.ok().body(groupService.getGroup(groupName));

    }


    @ResponseBody
    @GetMapping("GroupManager/getAll")
    public ResponseEntity<List<Group>> saveGroup() {

        return ResponseEntity.ok().body(groupService.getGroups());

    }

    @ResponseBody
    @GetMapping("GroupManager/getTime")
    public ResponseEntity<Date> getTimeGroup(@RequestBody String groupName) {

        return ResponseEntity.ok().body(groupService.getTimestamp(groupName));

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




