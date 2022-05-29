package Issue.Tracking.Tool.LoginSessionPoint.Api;

import Issue.Tracking.Tool.LoginSessionPoint.domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Privilege;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Role;
import Issue.Tracking.Tool.LoginSessionPoint.domain.deletedConfirmation;
import Issue.Tracking.Tool.LoginSessionPoint.domainAssamblers.APIUserModelAssembler;
import Issue.Tracking.Tool.LoginSessionPoint.domainAssamblers.RoleModelAssembler;
import Issue.Tracking.Tool.LoginSessionPoint.exception.*;
import Issue.Tracking.Tool.LoginSessionPoint.service.PrivService;
import Issue.Tracking.Tool.LoginSessionPoint.service.RoleService;
import Issue.Tracking.Tool.LoginSessionPoint.service.apiKeyPairService;
import Issue.Tracking.Tool.LoginSessionPoint.util.RoleUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.sql.Date;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;


import static Issue.Tracking.Tool.LoginSessionPoint.constants.MiscConfig.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.ResponseEntity.created;

@Controller
//@RequestMapping(path = "/LoginSessionPoint")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "localhost:4200")

public class UserServiceClass {
    private final Issue.Tracking.Tool.LoginSessionPoint.service.UserService userService;
    private final APIUserModelAssembler APIUserAssembler;
    private final RoleModelAssembler roleModelAssembler;
    private final RoleService roleService;
    private  final  AuthenticationManager  authenticationManager;
    private  final PrivService privService;
    private  final Issue.Tracking.Tool.LoginSessionPoint.service.apiKeyPairService apiKeyPairService;

    //private final SecurityService securityService;



    @ResponseBody

    @GetMapping("/user/{username}")
    public APIUser getUserByName(@PathVariable("username") String username) {

        return userService.getUser(username);
        // use fastAPIUser
    }


    @ResponseBody
    @PutMapping("/user/changePass")
    public void replacePassword(@RequestBody PasswordInput passwordInput) {


        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(11);


        APIUser userOld = userService.getUser(passwordInput.getUsername());
        if (userOld != null ){
            if ( encoder.matches(passwordInput.getOldPass() , userOld.getPassword())) {
                userOld.setPassword(passwordInput.getNewPass());
                userOld.setLastUpdated(Date.from(Instant.now()));


                userService.saveUser(userOld);
            }
            else throw new BadCredentialsException("Wrong Password" );
        }

        else throw new NoDataFoundException();

    }


    public void ifContains(List<APIUser> UserPool , APIUser user , List<Role> roles){

        if(user.getRoles().stream().anyMatch(roles::contains)){
            UserPool.add(user);
        }

    }

    @ResponseBody
    @GetMapping("/user/searchBy={ToSearch}")
    public List<APIUser> FindBy(@PathVariable String ToSearch) {

        List<APIUser> users = userService.findBy(ToSearch);

        log.info(users.toString() + " BY " + ToSearch);



        return users;


    }





    @ResponseBody
    @PutMapping("/user/update/{username}")
    public ResponseEntity<APIUser> replaceAPIUser(@RequestBody APIUser user, @PathVariable String username) {


         APIUser userOld = userService.getUser(username);
         if(userOld != null) {
             if (user.getUsername() != null) userOld.setUsername(user.getUsername());
             if (user.getPassword() != null) userOld.setPassword(user.getPassword());
             if (user.getRoles() != null) {

                 userOld.getRoles().clear();
                 user.getRoles().forEach(role -> userOld.getRoles().add(roleService.getRole(role.getName())));
             }
          /*   if (user.getApiKeys() != null) {
                 userOld.getApiKeys().clear();
                 userOld.getApiKeys().forEach(apiKeyPair -> user.getApiKeys().add(apiKeyPairService.generate()));
             }*/ // should not be updatable like this


             if (user.getEmail() != null) userOld.setEmail(user.getEmail());
             if (user.getName() != null) userOld.setName(user.getName());
             userOld.setLastUpdated(user.getCreatedAt());

             userService.saveUser(userOld);

             return ResponseEntity.ok().body(userOld);
         }
        else throw new NoDataFoundException();
    }

    @DeleteMapping("/user/delete/{username}")
    public ResponseEntity<deletedConfirmation> deleteUser(@PathVariable String username) {

        deletedConfirmation del = null;
        if (userService.getUser(username) != null) {
            del = new deletedConfirmation(username);
            userService.deleteByUsername(username);
        }

        return ResponseEntity.ok().body(del);
    }


    @ResponseBody
    @GetMapping("/users/Index={PageIndex}&Size={PageSize}&By={SortBy}&Direction={SortDirection}")
    public CollectionModel<EntityModel<APIUser>> getUsers( @PathVariable Integer PageIndex , @PathVariable Integer PageSize ,
                                                           @PathVariable String SortBy , @PathVariable Integer SortDirection  ) {
        List<EntityModel<APIUser>> users = userService.getUsers(PageIndex,PageSize
                        ,SortBy,SortDirection).stream() //
                .map(APIUserAssembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(users, linkTo(methodOn(UserServiceClass.class).getUsers(PageIndex,PageSize
                ,SortBy,SortDirection)).withSelfRel());


    }

    @ResponseBody
    @GetMapping("/users")
    public ResponseEntity<List<APIUser>> getUsersALL() {
       // List<EntityModel<APIUser>> users = userService.getUsersALL().stream() //
           //     .map(APIUserAssembler::toModel) //
             //   .collect(Collectors.toList());

       // return CollectionModel.of(users, linkTo(methodOn(UserServiceClass.class).getUsersALL()).withSelfRel());

            return ResponseEntity.ok().body(userService.getUsersALL());
    }



    //testing only
    @ResponseBody
    @GetMapping("role")
    public ResponseEntity<List<Role>> getALLRoles() {

       // List<EntityModel<Role>> roles = roleService.getALLRoles().stream() //
            //    .map(roleModelAssembler::toModel) //
             //   .collect(Collectors.toList());

        return ResponseEntity.ok().body(roleService.getALLRoles());

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
    public ResponseEntity<Role> replaceRole(@RequestBody Role role, @PathVariable String name) {

        Role roleOld = roleService.getRole(name);
        if(roleOld != null) {
            if (role.getName() != null) roleOld.setName(role.getName());
            roleOld.setLastUpdated(role.getCreatedAt());

            if(role.getPrivileges() != null){
                roleOld.getPrivileges().clear();
                role.getPrivileges().forEach(privilege -> roleOld.getPrivileges().add(privService.findByName(privilege.getName())));

            }

            roleService.saveRole(roleOld);
            return ResponseEntity.ok().body(roleOld);
        }
        else throw new NoDataFoundException();
    }


    @DeleteMapping("/role/delete/{name}")
    public ResponseEntity<deletedConfirmation> deleteRole(@PathVariable String name) {
        deletedConfirmation del = new deletedConfirmation();
        if (!DEFAULT_ROLES.contains(name)) {

            roleService.deleteByName(name);
            del.setDeleted(name);
        }
        else throw new IllegalDefaultException();

        return  ResponseEntity.ok().body(del);
    }

    //testing only
    @ResponseBody
    @PostMapping("user/save")
    public ResponseEntity<APIUser> saveUser(@RequestBody APIUser user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/save").toUriString());
        return created(uri).body(userService.saveUser(user));
    }

    @ResponseBody
    @PostMapping("user/register")
    public Map<String, String> registerUser(@RequestBody APIUser user, HttpServletRequest request, HttpServletResponse response)  {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/register").toUriString());


        if(user.getPassword() == null || user.getUsername() == null || user.getName() == null || user.getEmail() == null)
            throw new PasswordMissingException();
        if(userService.getAllUsernames().contains(user.getUsername()))
            throw new AlreadyExistsException(APIUser.class);

       // if(!user.getEmail().matches("/^(.+)@(\\\\S+)$"))
           // throw  new InvalidMailException();




        user.getRoles().add(roleService.getRole("ROLE_USER"));
        log.info("ROLES ARE:" + user.getRoles());


        String pass = user.getPassword();
        userService.saveUser(user);

        try {

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.getUsername(), pass);
            authToken.setDetails(new WebAuthenticationDetails(request));

            Authentication authentication = authenticationManager.authenticate(authToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);


            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

            /*
            List<Role> AuthRoles = (List<Role>) user.getRoles();
            Set<String> claims = AuthRoles.stream().map(role -> role.getPrivileges().stream().map(Privilege::getName).collect(Collectors.toList())).collect(Collectors.toList()).stream().flatMap(Collection::stream).collect(Collectors.toSet()); //.map(strings -> strings)//.collect(Collectors.joining(","));
            List<String>  claimsNew = new ArrayList<>(claims);
            */

            String access_token = JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(new java.util.Date(System.currentTimeMillis() + 10L * 6000 * TOKEN_EXPIRATION_TIME_MINS))  // Token Expiress at 20 mins
                    .withIssuer(request.getRequestURL().toString())
                    .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                    .sign(algorithm);


            String refresh_token = JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(new java.util.Date(System.currentTimeMillis() + 10L * 6000 * REFRESH_TOKEN_EXPIRATION_TIME_MINS))  // Token Expiress at 60 mins
                    .withIssuer(request.getRequestURL().toString())
                    .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                    .sign(algorithm);

        /*response.setHeader("access_token", access_token);
        response.setHeader("refresh_token", refresh_token);*/

            Map<String, String> tokens = new HashMap<>();
            tokens.put("access_token", access_token);
            tokens.put("refresh_token", refresh_token);
            return tokens;
        } catch(Exception ex) {
            log.error(ex.getMessage());
            return new HashMap<>();
        }

    }


    @ResponseBody
    @GetMapping("role/privs")
    public ResponseEntity<List<Privilege>> getALLPrivs() {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("role/privs").toUriString());


        return created(uri).body(privService.getAll());

    }



    @ResponseBody
    @PostMapping("role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/role/save").toUriString());

        for(Privilege p : role.getPrivileges())
            if(privService.findByName(p.getName()) == null)
                throw new NoDataFoundException();


        return created(uri).body(roleService.saveRole(role));
    }



    @ResponseBody
    @PostMapping("role/addtouser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @GetMapping("/role/searchBy={ToSearch}")
    public List<Role> RFindBy(@PathVariable String ToSearch) {

        List<Role> roles = roleService.findByTerm(ToSearch);

        log.info(roles.toString() + " BY " + ToSearch);



        return roles;


    }


}


@Data
class PasswordInput {
    private String username;
    private String oldPass;
    private String newPass;
}

