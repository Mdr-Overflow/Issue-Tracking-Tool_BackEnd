package Issue.Tracking.Tool.LoginSessionPoint.Api;

import Issue.Tracking.Tool.LoginSessionPoint.domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Role;
import Issue.Tracking.Tool.LoginSessionPoint.domainAssamblers.APIUserModelAssembler;
import Issue.Tracking.Tool.LoginSessionPoint.domainAssamblers.RoleModelAssembler;
import Issue.Tracking.Tool.LoginSessionPoint.exception.IllegalDefaultException;
import Issue.Tracking.Tool.LoginSessionPoint.exception.NoDataFoundException;
import Issue.Tracking.Tool.LoginSessionPoint.service.RoleService;
import Issue.Tracking.Tool.LoginSessionPoint.service.UserGroupService;
import Issue.Tracking.Tool.LoginSessionPoint.service.IssueService;
import Issue.Tracking.Tool.LoginSessionPoint.service.SolutionService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


import static Issue.Tracking.Tool.LoginSessionPoint.constants.MiscConfig.DEFAULT_ROLES;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.ResponseEntity.created;

@Controller
//@RequestMapping(path = "/LoginSessionPoint")
@RequiredArgsConstructor

public class UserServiceClass {
    private final Issue.Tracking.Tool.LoginSessionPoint.service.UserService userService;
    private final APIUserModelAssembler APIUserAssembler;
    private final RoleModelAssembler roleModelAssembler;
    private final RoleService roleService;

    //private final SecurityService securityService;



    @ResponseBody
    @CrossOrigin(origins = "localhost:4200")
    @GetMapping("/user/{username}")
    public EntityModel<APIUser> getUserByName(@PathVariable("username") String username) {
        APIUser user = userService.getUser(username);
        return APIUserAssembler.toModel(user);
        // use fastAPIUser
    }


    @ResponseBody
    @PutMapping("/user/changePass")
    public void replacePassword(@RequestBody PasswordInput passwordInput) {


        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(11);


        APIUser userOld = userService.getUser(passwordInput.getUsername());
        if (userOld != null ){
            if ( encoder.matches(passwordInput.getOldPass() , userOld.getPassword())) {
                userOld.setPassword(encoder.encode(passwordInput.getNewPass()));
                userOld.setLastUpdated(Date.from(Instant.now()));


                userService.saveUser(userOld);
            }
            else throw new BadCredentialsException("Wrong Password");
        }

        else throw new NoDataFoundException();

    }



    @ResponseBody
    @PutMapping("/user/update/{username}")
    public String replaceAPIUser(@RequestBody APIUser user, @PathVariable String username) {

         APIUser userOld = userService.getUser(username);
         if(userOld != null) {
             if (user.getUsername() != null) userOld.setUsername(user.getUsername());
             if (user.getPassword() != null) userOld.setPassword(user.getPassword());
             if (user.getRoles() != null) userOld.setRoles(user.getRoles());
             if (user.getApiKeys() != null) userOld.setApiKeys(user.getApiKeys());
             if (user.getEmail() != null) userOld.setEmail(user.getEmail());
             if (user.getName() != null) userOld.setName(user.getName());
             userOld.setLastUpdated(user.getCreatedAt());

             userService.saveUser(userOld);

             return "updated";
         }
        else throw new NoDataFoundException();
    }

    @DeleteMapping("/user/delete/{username}")
    void deleteUser(@PathVariable String username) {
        userService.deleteByUsername(username);
    }


    @ResponseBody
    @GetMapping("/user")
    public CollectionModel<EntityModel<APIUser>> getUsers() {
        List<EntityModel<APIUser>> users = userService.getUsers().stream() //
                .map(APIUserAssembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(users, linkTo(methodOn(UserServiceClass.class).getUsers()).withSelfRel());


    }


    //testing only
    @ResponseBody
    @GetMapping("role")
    public CollectionModel<EntityModel<Role>> getALLRoles() {

        List<EntityModel<Role>> roles = roleService.getALLRoles().stream() //
                .map(roleModelAssembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(roles, linkTo(methodOn(UserServiceClass.class).getALLRoles()).withSelfRel());

    }

    @ResponseBody
    @GetMapping("role/{name}")
    public ResponseEntity<Role> getRole(String name) {

        return ResponseEntity.ok().body(roleService.getRole(name));

    }


    //testing only

    // @GetMapping

    @ResponseBody
    @PutMapping("/role/update/{name}")
    public String replaceRole(@RequestBody Role role, @PathVariable String name) {

        Role roleOld = roleService.getRole(name);
        if(roleOld != null) {
            if (role.getName() != null) roleOld.setName(role.getName());
            roleOld.setLastUpdated(role.getCreatedAt());


            roleService.saveRole(roleOld);
            return "updated";
        }
        else throw new NoDataFoundException();
    }


    @DeleteMapping("/role/delete/{name}")
    public void deleteRole(@PathVariable String name) {
        if (!DEFAULT_ROLES.contains(name))
        roleService.deleteByName(name);
        else throw new IllegalDefaultException();
    }

    //testing only
    @ResponseBody
    @PostMapping("user/save")
    public ResponseEntity<APIUser> saveUser(@RequestBody APIUser user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/save").toUriString());
        return created(uri).body(userService.saveUser(user));
    }

    @ResponseBody
    @PostMapping("role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/role/save").toUriString());
        return created(uri).body(roleService.saveRole(role));
    }

    @ResponseBody
    @PostMapping("role/addtouser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

}


@Data
class PasswordInput {
    private String username;
    private String oldPass;
    private String newPass;
}



